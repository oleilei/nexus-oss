/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2015 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.plugins.capabilities.internal.condition;

import java.security.NoSuchAlgorithmException;

import org.sonatype.nexus.plugins.capabilities.Condition;
import org.sonatype.nexus.plugins.capabilities.support.condition.ConditionSupport;
import org.sonatype.sisu.goodies.crypto.CryptoHelper;
import org.sonatype.sisu.goodies.eventbus.EventBus;
import org.sonatype.sisu.goodies.i18n.I18N;
import org.sonatype.sisu.goodies.i18n.MessageBundle;

import org.jetbrains.annotations.NonNls;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A condition that is satisfied if the JVM supports unlimited-strength keys for a specific cipher algorithm.
 *
 * @since 2.7
 */
public class CipherKeyUnlimitedStrengthCondition
    extends ConditionSupport
    implements Condition
{
  /**
   * The minimum bit-length that is considered "unlimited-strength".
   */
  public static final int MIN_BITS = Integer.MAX_VALUE;

  private static interface Messages
      extends MessageBundle
  {
    @DefaultMessage("JVM supports unlimited-strength '%s' cipher keys")
    String satisfied(String transformation);

    @DefaultMessage("JVM does NOT support unlimited-strength '%s' cipher keys")
    String unsatisfied(String transformation);
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final CryptoHelper crypto;

  private final String transformation;

  public CipherKeyUnlimitedStrengthCondition(final EventBus eventBus,
                                             final CryptoHelper crypto,
                                             final @NonNls String transformation)
  {
    super(eventBus, false);
    this.crypto = checkNotNull(crypto);
    this.transformation = checkNotNull(transformation);
  }

  @Override
  protected void doBind() {
    getEventBus().register(this);
    verify();
  }

  @Override
  protected void doRelease() {
    getEventBus().unregister(this);
  }

  @Override
  public String toString() {
    return explainSatisfied();
  }

  @Override
  public String explainSatisfied() {
    return messages.satisfied(transformation);
  }

  @Override
  public String explainUnsatisfied() {
    return messages.unsatisfied(transformation);
  }

  private void verify() {
    try {
      int max = crypto.getCipherMaxAllowedKeyLength(transformation);
      if (max >= MIN_BITS) {
        setSatisfied(true);
      }
      else {
        setSatisfied(false);
      }
    }
    catch (NoSuchAlgorithmException e) {
      setSatisfied(false);
    }
  }
}
