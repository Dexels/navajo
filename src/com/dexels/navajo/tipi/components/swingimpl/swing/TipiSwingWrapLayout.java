package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;

import javax.swing.*;

/**
 * FlowLayout subclass that fully supports wrapping of components.

 */
public class TipiSwingWrapLayout
    extends FlowLayout {
  /**
   * The preferred size for layout.

   */
  private Dimension preferredLayoutSize;
  /**
   * Constructs a new <code>WrappingFlowLayout</code> with a centered

   * alignment and a default 5-unit horizontal and vertical gap.

   */
  public TipiSwingWrapLayout() {
    super();
  }

  /**
   * Constructs a new <code>FlowLayout</code> with the specified
   * alignment and a default 5-unit horizontal and vertical gap.
   * The value of the alignment argument must be one of
   * <code>WrappingFlowLayout</code>, <code>WrappingFlowLayout</code>,
   * or <code>WrappingFlowLayout</code>.
   * @param align the alignment value
   */
  public TipiSwingWrapLayout(int align) {
    super(align);
  }

  /**
   * Creates a new flow layout manager with the indicated alignment

   * and the indicated horizontal and vertical gaps.

   * <p>

   * The value of the alignment argument must be one of

   * <code>WrappingFlowLayout</code>, <code>WrappingFlowLayout</code>,

   * or <code>WrappingFlowLayout</code>.

   * @param      align   the alignment value

   * @param      hgap    the horizontal gap between components

   * @param      vgap    the vertical gap between components

   */
  public TipiSwingWrapLayout(int align, int hgap, int vgap) {
    super(align, hgap, vgap);
  }

  /**
   * Returns the preferred dimensions for this layout given the

   * <i>visible</i> components in the specified target container.

   * @param target the component which needs to be laid out

   * @return    the preferred dimensions to lay out the

   *            subcomponents of the specified container

   */
  public Dimension preferredLayoutSize(Container target) {
    return layoutSize(target, true);
  }

  /**
   * Returns the minimum dimensions needed to layout the <i>visible</i>

   * components contained in the specified target container.

   * @param target the component which needs to be laid out

   * @return    the minimum dimensions to lay out the

   *            subcomponents of the specified container

   */
  public Dimension minimumLayoutSize(Container target) {
    return layoutSize(target, false);
  }

  /**
   * Returns the minimum or preferred dimension needed to layout the target

   * container.

   *

   * @param target        target to get layout size for

   * @param preferred     should preferred size be calculated

   * @return              the dimension to layout the target container

   */
  private Dimension layoutSize(Container target, boolean preferred) {
    synchronized (target.getTreeLock()) {
      Insets insets = target.getInsets();
      int maxWidth = target.getWidth() -
          (insets.left + insets.right + getHgap() * 2);
      Dimension dim = new Dimension(0, 0);
      int rowWidth = 0;
      int rowHeight = 0;
      int nmembers = target.getComponentCount();
      for (int i = 0; i < nmembers; i++) {
        Component m = target.getComponent(i);
        if (m.isVisible()) {
          Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
          if (rowWidth + d.width > maxWidth) {
            addRow(dim, rowWidth, rowHeight);
            rowWidth = 0;
            rowHeight = 0;
          }
          if (rowWidth > 0) {
            rowWidth += getHgap();
          }
          rowWidth += d.width;
          rowHeight = Math.max(rowHeight, d.height);
        }
      }
      addRow(dim, rowWidth, rowHeight);
      dim.width += insets.left + insets.right + getHgap() * 2;
      dim.height += insets.top + insets.bottom + getVgap() * 2;
      return dim;
    }
  }

  /**
   * Add a new row to the given dimension.

   *

   * @param dim       dimension to add row to

   * @param rowWidth  the width of the row to add

   * @param rowHeight the height of the row to add

   */
  private final void addRow(Dimension dim, int rowWidth, int rowHeight) {
    dim.width = Math.max(dim.width, rowWidth);
    if (dim.height > 0) {
      dim.height += getVgap();
    }
    dim.height += rowHeight;
  }

  /**
   * Lays out the container. This method lets each component take

   * its preferred size by reshaping the components in the

   * target container in order to satisfy the alignment of

   * this <code>WrappingFlowLayout</code> object.

   * @param target the specified component being laid out

   */
  public void layoutContainer(final Container target) {
    super.layoutContainer(target);
    Dimension size = preferredLayoutSize(target);
    if (!size.equals(preferredLayoutSize)) {
      preferredLayoutSize = size;
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          target.invalidate();
          target.getParent().validate();
        }
      });
    }
  }
}
