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
package org.sonatype.nexus.plugins.capabilities.support.condition;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.plugins.capabilities.Condition;
import org.sonatype.nexus.plugins.capabilities.internal.condition.CipherKeyHighStrengthCondition;
import org.sonatype.nexus.plugins.capabilities.internal.condition.CipherKeyUnlimitedStrengthCondition;
import org.sonatype.nexus.plugins.capabilities.internal.condition.CipherRequiredCondition;
import org.sonatype.sisu.goodies.crypto.CryptoHelper;
import org.sonatype.sisu.goodies.eventbus.EventBus;

import org.jetbrains.annotations.NonNls;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory of {@link Condition}s related to crypto support.
 *
 * @since 2.7
 */
@Named
@Singleton
public class CryptoConditions
{
  private final EventBus eventBus;

  private final CryptoHelper crypto;

  @Inject
  public CryptoConditions(final EventBus eventBus,
                          final CryptoHelper crypto)
  {
    this.eventBus = checkNotNull(eventBus);
    this.crypto = checkNotNull(crypto);
  }

  public Condition requireCipher(final @NonNls String algorithm) {
    return new CipherRequiredCondition(eventBus, crypto, algorithm);
  }

  public Condition highStrengthCipherKey(final @NonNls String algorithm) {
    return new CipherKeyHighStrengthCondition(eventBus, crypto, algorithm);
  }

  public Condition unlimitedStrengthCipherKey(final @NonNls String algorithm) {
    return new CipherKeyUnlimitedStrengthCondition(eventBus, crypto, algorithm);
  }
}
