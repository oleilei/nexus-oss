/**
 * Copyright (c) 2008-2011 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions
 *
 * This program is free software: you can redistribute it and/or modify it only under the terms of the GNU Affero General
 * Public License Version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License Version 3
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License Version 3 along with this program.  If not, see
 * http://www.gnu.org/licenses.
 *
 * Sonatype Nexus (TM) Open Source Version is available from Sonatype, Inc. Sonatype and Sonatype Nexus are trademarks of
 * Sonatype, Inc. Apache Maven is a trademark of the Apache Foundation. M2Eclipse is a trademark of the Eclipse Foundation.
 * All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.plugins.capabilities.internal.activation;

import org.sonatype.nexus.plugins.capabilities.api.activation.Condition;

/**
 * A condition that is never satisfied.
 *
 * @since 2.0
 */
public class UnsatisfiedCondition
    implements Condition
{

    private final String reason;

    public UnsatisfiedCondition( final String reason )
    {
        this.reason = reason;
    }

    @Override
    public boolean isSatisfied()
    {
        return false;
    }

    @Override
    public UnsatisfiedCondition bind()
    {
        // do nothing
        return this;
    }

    @Override
    public UnsatisfiedCondition release()
    {
        // do nothing
        return this;
    }

    @Override
    public String toString()
    {
        return reason;
    }

    @Override
    public String explainSatisfied()
    {
        return "Not " + reason;
    }

    @Override
    public String explainUnsatisfied()
    {
        return reason;
    }
}
