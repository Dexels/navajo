package com.dexels.navajo.mapping;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class MappableTreeNode {

        public MappableTreeNode parent = null;
        public Object myObject = null;
        public String name = "";
        public String ref = "";

        public MappableTreeNode(MappableTreeNode parent, Object o) {
            this.parent = parent;
            this.myObject = o;
        }

    }
