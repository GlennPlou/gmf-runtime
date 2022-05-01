/******************************************************************************
 * Copyright (c) 2004, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.gmf.tests.runtime.gef.ui;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.RectangularDropShadowLineBorder;
import org.eclipse.gmf.runtime.draw2d.ui.internal.graphics.ScalableFreeformLayeredPane;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeTypes;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.gef.ui.internal.figures.DiamondFigure;

import junit.framework.TestCase;

/**
 * @author oboyko
 *
 */
@SuppressWarnings("restriction")
public class FigureSlidableAnchorTests
	extends TestCase {
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testStraightlineTolerance() {
		NodeFigure fig = new NodeFigure();
		fig.setBounds(new Rectangle(100,100, 100, 100));
		
		// default anchor test 
		Point p1 = new Point(150,150); 
		Point p2 = new Point(148,300); 
		ConnectionAnchor anchor = fig.getSourceConnectionAnchorAt(p1);
		Point reference = anchor.getLocation(p2);
		assertEquals(p2.preciseX(), reference.preciseX(), 0);

		// Sliding anchor test inside figure's bounds
		p1 = new Point(110,200); 
		p2 = new Point(112,300); 
		anchor = fig.getSourceConnectionAnchorAt(p1);
		reference = anchor.getLocation(p2);
		assertEquals(p2.preciseX(), reference.preciseX(), 0);
	}
	
	public void testGetLocationFromReferenceOnBorder() {
		NodeFigure fig = new NodeFigure();
		fig.setBounds(new Rectangle(100,100, 100, 100));
		
		Point p1 = new Point(200,150); 
		ConnectionAnchor anchor = fig.getSourceConnectionAnchorAt(p1);
		
		// verify when reference point is on or close to border, the returned location
		// is the same point.
		Point p2 = new Point(200, 175); 
		Point reference = anchor.getLocation(new Point(200, 175));
		assertEquals(p2.preciseY(), reference.preciseY(), 0);
	}
	
	public void testAnchorPosition() {
		NodeFigure fig = new NodeFigure();
		fig.setBounds(new Rectangle(-10,-10, 20, 20));
		
		// default anchor test 
		Point p = new Point(3,3); 
		ConnectionAnchor anchor = fig.getSourceConnectionAnchorAt(p);
		Point reference = anchor.getReferencePoint();
		assertEquals(0, reference.preciseX(), 0);
		assertEquals(0, reference.preciseY(), 0);

		// Sliding anchor test inside figure's bounds
		p = new Point(8,8); 
		anchor = fig.getSourceConnectionAnchorAt(p);
		reference = anchor.getReferencePoint();
		assertEquals(p.preciseX(), reference.preciseX(), 0);
		assertEquals(p.preciseY(), reference.preciseY(), 0);

		// Sliding anchor test outside figure's bounds
		p = new Point(12,12); 
		anchor = fig.getSourceConnectionAnchorAt(p);
		reference = anchor.getReferencePoint();
		assertEquals(10, reference.preciseX(), 0);
		assertEquals(10, reference.preciseY(), 0);
		
		p = new Point(15,0); 
		anchor = fig.getSourceConnectionAnchorAt(p);
		reference = anchor.getReferencePoint();
		assertEquals(10, reference.preciseX(), 0);
		assertEquals(0, reference.preciseY(), 0);
	}
	
	public void testAnchorLocationOnPolygonFigure() {
		DiamondFigure fig = new DiamondFigure(new Dimension(1058, 1058));
		fig.setBounds(Rectangle.SINGLETON);
		fig.getBounds().setLocation(-100,-100);
		fig.getBounds().setSize(200,200);
		Point reference = new Point(200,0);
		
		// Default anchor location
		Point position = new Point(20,20);
		ConnectionAnchor anchor = fig.getSourceConnectionAnchorAt(position);
		Point location = anchor.getLocation(reference);
		assertEquals(100, location.preciseX(), 0);
		assertEquals(0, location.preciseY(), 0);
		
		// Sliding anchor location inside the figure
		position = new Point(-80,0);
		anchor = fig.getSourceConnectionAnchorAt(position);
		location = anchor.getLocation(reference);
		assertEquals(100, location.preciseX(), 0);
		assertEquals(0, location.preciseY(), 0);
		
		// Sliding anchor location outside the figure
		position = new Point(100,100);
		anchor = fig.getSourceConnectionAnchorAt(position);
		location = anchor.getLocation(reference);
		assertEquals(100, location.preciseX(), 0);
		assertEquals(0, location.preciseY(), 0);
		
		// Sliding anchor location on the diamond shape vertex
		position = new Point(100,50);
		anchor = fig.getSourceConnectionAnchorAt(position);
		location = anchor.getLocation(reference);
		assertEquals(0, location.preciseX(), 0);
		assertEquals(100, location.preciseY(), 0);

		// Reference inside the figure
		reference = new Point(-20,50);
		location = anchor.getLocation(reference);
		assertEquals(-50, location.preciseX(), 0);
		assertEquals(50, location.preciseY(), 0);
		
		// Reference and anchor's position are equal
		reference = new Point(100,50);
		location = anchor.getLocation(reference);
		assertEquals(200d/3d, location.preciseX(), 0);
		assertEquals(100d/3d, location.preciseY(), 0);
		
		// Reference point is at the center of the figure as well as the anchors position
		reference = new Point(0,0);
		position = new Point (0,0);
		anchor = fig.getSourceConnectionAnchorAt(position);
		location = anchor.getLocation(reference);
		assertEquals(0, location.preciseX(), 0);
		assertEquals(0, location.preciseY(), 0);
	}

    public void testAnchorPositionForFigureWithTransparentBorder() {
        // Create a node figure
        NodeFigure fig = new NodeFigure();
        fig.setBounds(new Rectangle(-10, -10, 20, 20));
        // Add a transparent border figure with mock methods to avoid a full
        // transparent border figure implementation
        RectangularDropShadowLineBorder border = new RectangularDropShadowLineBorder(2) {
            @Override
            protected int getShadowHeight() {
                return 2;
            }

            @Override
            protected int getShadowWidth() {
                return 2;
            }
        };
        border.setShouldDrawDropShadow(false);
        fig.setBorder(border);
        // Add the node figure to another figure (IMapModeHolder) to have an
        // Identity coordinate mapping (used during computation of anchor).
        ScalableFreeformLayeredPane scalableFreeformLayeredPane = new ScalableFreeformLayeredPane(MapModeTypes.IDENTITY_MM);
        scalableFreeformLayeredPane.add(fig);

        // Do the same tests as testAnchorPosition()
        // default anchor test (center of box)
        Point p = new Point(3, 3);
        ConnectionAnchor anchor = fig.getSourceConnectionAnchorAt(p);
        Point reference = anchor.getReferencePoint();
        assertEquals(-1, reference.preciseX(), 0);
        assertEquals(-1, reference.preciseY(), 0);

        // Sliding anchor test inside figure's bounds (the most important is
        // this case, the other tests use constant values as expected value)
        p = new Point(8, 8);
        anchor = fig.getSourceConnectionAnchorAt(p);
        reference = anchor.getReferencePoint();
        assertEquals(p.preciseX(), reference.preciseX(), 0);
        assertEquals(p.preciseY(), reference.preciseY(), 0);

        // Sliding anchor test outside figure's bounds
        p = new Point(12, 12);
        anchor = fig.getSourceConnectionAnchorAt(p);
        reference = anchor.getReferencePoint();
        assertEquals(8, reference.preciseX(), 0);
        assertEquals(8, reference.preciseY(), 0);

        p = new Point(15, 0);
        anchor = fig.getSourceConnectionAnchorAt(p);
        reference = anchor.getReferencePoint();
        assertEquals(8, reference.preciseX(), 0);
        assertEquals(0, reference.preciseY(), 0);
    }
}
