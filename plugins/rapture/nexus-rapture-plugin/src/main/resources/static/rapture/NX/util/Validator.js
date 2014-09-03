/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2014 Sonatype, Inc.
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
 * Validation helpers.
 *
 * @since 3.0
 */
Ext.define('NX.util.Validator', {
  singleton: true,

  // isURL validation based on: https://github.com/chriso/validator.js (MIT license)

  /**
   * @private
   */
  default_url_options: {
    protocols: ['http', 'https', 'ftp'],
    require_tld: true,
    require_protocol: false,
    allow_underscores: false
  },

  /**
   * Validate if given string is a URL.
   *
   * @public
   * @param {String} str
   * @param {Object} options (optional)
   * @returns {boolean}
   */
  isURL: function (str, options) {
    if (!str || str.length >= 2083) {
      return false;
    }
    options = options || {};
    options = Ext.applyIf(options, this.default_url_options);
    var separators = '-?-?' + (options.allow_underscores ? '_?' : '');
    var url = new RegExp('^(?!mailto:)(?:(?:' + options.protocols.join('|') + ')://)' +
        (options.require_protocol ? '' : '?') +
        '(?:\\S+(?::\\S*)?@)?(?:(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[0-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:www.)?)?(?:(?:[a-z\\u00a1-\\uffff0-9]+' +
        separators + ')*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+' + separators +
        ')*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))' + (options.require_tld ? '' : '?') +
        ')|localhost)(?::(\\d{1,5}))?(?:(?:/|\\?|#)[^\\s]*)?$', 'i');

    var match = str.match(url),
        port = match ? match[1] : 0;
    return !!(match && (!port || (port > 0 && port <= 65535)));
  }

});