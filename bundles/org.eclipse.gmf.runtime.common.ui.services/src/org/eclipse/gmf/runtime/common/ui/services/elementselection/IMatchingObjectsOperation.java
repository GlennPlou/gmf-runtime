/******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/
package org.eclipse.gmf.runtime.common.ui.services.elementselection;

import org.eclipse.gmf.runtime.common.core.service.IOperation;

/**
 * The matching objects operation used by the element selection service.
 * 
 * @author Anthony Hunter
 */
public interface IMatchingObjectsOperation
    extends IOperation {
    
    /**
     * Retreive the element selection input for this operation.
     * 
     * @return the element selection input.
     */
    public IElementSelectionInput getElementSelectionInput();
}
