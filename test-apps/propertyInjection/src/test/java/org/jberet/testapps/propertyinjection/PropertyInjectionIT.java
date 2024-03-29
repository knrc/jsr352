/*
 * Copyright (c) 2012-2013 Red Hat, Inc. and/or its affiliates.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Cheng Fang - Initial API and implementation
 */

package org.jberet.testapps.propertyinjection;

import junit.framework.Assert;
import org.jberet.testapps.common.AbstractIT;
import org.junit.Test;

public class PropertyInjectionIT extends AbstractIT {
    @Test
    public void propertyInjection() throws Exception {
        startJobAndWait("propertyInjection.xml");
        Assert.assertEquals("ab 2ab2 2default2 null", stepExecution0.getExitStatus());
    }
}
