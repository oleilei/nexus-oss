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
package org.sonatype.nexus.yum.client.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capabilities.client.spi.CapabilityFactory;
import org.sonatype.nexus.capabilities.client.support.ReflectiveCapabilityFactory;
import org.sonatype.nexus.yum.client.capabilities.ProxyMetadataCapability;

/**
 * Jersey Proxy Metadata capability factory.
 *
 * @since 2.7.0
 */
@Named
@Singleton
public class ProxyMetadataCapabilityFactory
    extends ReflectiveCapabilityFactory<ProxyMetadataCapability>
    implements CapabilityFactory<ProxyMetadataCapability>
{

  public ProxyMetadataCapabilityFactory() {
    super(ProxyMetadataCapability.class);
  }

}
