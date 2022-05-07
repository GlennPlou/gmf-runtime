/******************************************************************************
 * Copyright (c) 2002, 2005 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.gmf.runtime.common.ui.services.internal.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.gmf.runtime.common.core.service.IProvider;
import org.eclipse.ui.IEditorPart;

/**
 * This interface enables clients to handle the navigation when the user goes to
 * (double clicks) a marker that the client has defined. The client can either
 * perform the necessary feedback in their provider or can delegate the
 * operation via an applicable mechanism (e.g., a GEF request).
 * 
 * @author Kevin Cornell
 */
public interface IMarkerNavigationProvider
	extends IProvider {

	/**
	 * Perform feedback for marker navigation.
	 * 
	 * @param editor
	 *            the editor opened with the associated resource
	 * @param marker
	 *            the marker reference
	 */
	public void gotoMarker(IEditorPart editor, IMarker marker);

}