/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* 
slight modifications for ourontario newspaper application.
*/

package org.conifer.reading.imgop;

import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.WritableRaster;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;

import org.apache.avalon.framework.parameters.Parameters;

public class ScaleOperation
    implements ImgOperation {

    private String  prefix;
    private boolean enabled;
    private float scale;
    private int x0;
    private int y0;
    private int x1;
    private int y1;

    public void setPrefix( String prefix ) {
        this.prefix = prefix;
    }

    public void setup( Parameters params ) {
        enabled = params.getParameterAsBoolean( prefix + "enabled", true);
        scale = params.getParameterAsFloat( prefix + "scale", 1.0f );
        x0 = params.getParameterAsInteger( prefix + "x0", 0 );
        y0 = params.getParameterAsInteger( prefix + "y0", 0 );
        x1 = params.getParameterAsInteger( prefix + "x1", 0 );
        y1 = params.getParameterAsInteger( prefix + "y1", 0 );
    }

    public BufferedImage apply( BufferedImage image ) {
        int newWidth = new Double(image.getWidth() * scale).intValue();
        int newHeight = new Double(image.getHeight() * scale).intValue();

        BufferedImage resized = new BufferedImage(newWidth, newHeight, image.getType());
        BufferedImage quadrant = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        Graphics2D g = resized.createGraphics();
        Graphics2D g2 = quadrant.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
           
        //because this is used for tiles, it seems to be more efficient to scale the entire image first  
        g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, 
            image.getWidth(), image.getHeight(), null);

        //now draw the desired rectangle
        g2.drawImage(resized, 0, 0, image.getWidth(), image.getHeight(), x0, y0, 
            x1, y1, null);

        //clean up
        g.dispose();
        g2.dispose();

        return quadrant;
    }

    public String getKey() {
        return "scale:" 
               + ( enabled ? "enable" : "disable" )
               + ":" + scale
               + ":" + x0
               + ":" + y0
               + ":" + x1
               + ":" + y1
               + ":" + prefix;
    }
} 
