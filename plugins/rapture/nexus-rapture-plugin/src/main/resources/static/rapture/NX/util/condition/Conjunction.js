/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-2015 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
/*global Ext*/

/**
 * A {@link NX.util.condition.Condition} that is satisfied when all AND-ed {@link NX.util.condition.Condition}s
 * are satisfied.
 *
 * @since 3.0
 */
Ext.define('NX.util.condition.Conjunction', {
  extend: 'NX.util.condition.Condition',

  /**
   * @cfg {NX.util.condition.Condition[]} Array of conditions to be AND-ed
   */
  conditions: undefined,

  bind: function () {
    var me = this;

    if (!me.bounded) {
      me.callParent();
      me.evaluate();
      Ext.each(me.conditions, function (condition) {
        me.mon(condition, {
          satisfied: me.evaluate,
          unsatisfied: me.evaluate,
          scope: me
        });
      });
    }

    return me;
  },

  evaluate: function () {
    var me = this,
        satisfied = true;

    if (me.bounded) {
      Ext.each(me.conditions, function (condition) {
        satisfied = condition.satisfied;
        return satisfied;
      });
      me.setSatisfied(satisfied);
    }
  },

  toString: function () {
    var me = this;
    return me.conditions.join(' AND ');
  }

});