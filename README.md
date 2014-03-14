cecil
=====

Cocoon 2.2 viewer support, tile-based images and use of IA reader.

_ cocoon-serializers-conifer_

This a cocoon serializer to be built against trunk. If you just want to use
the serializer itself, you just need the jar file. The application uses the
Java Advanced Imaging (JAI) library for handling images. The JAI achieves
the greatest efficiency at the time of this writing using TIFF files.

Python has met and maybe even surpassed Java's image capabilities, and I
would like to port this to django at some point. 

The serializer is invoked through _sitemap.xmap_:

```
<map:match pattern="tiletest">
   <map:generate src="content/test.xml"/>	                     
   <map:serialize type="tile"/>            
</map:match>
```

The serializer is identified through _tile_ in this example. The _test.xml_ file
would look something like:

```
<tile:tileInfo xmlns:tile="http://ourontario.ca/tile/1.0">
        <tile:image flow="true" z="1" h="200" w="200" x1="1419" y1="2116" x2="1920" y2="2170"
        overlay="/openils/var/images/overlay.jpg"
        index="/openils/var/images/swoda/newspapers/squamish/08_1948/odw/bc002/index/0"
        query="summer"
        src="/openils/var/images/swoda/newspapers/squamish/08_1948/bc002.jpg"/>
</tile:tileInfo>
```

The attributes provide all of the details for the zoom level and size of the result. The index
has the lucene index with the coordinates.

art rhyno [conifer/hackforge/ourdigitalworld] (https://github.com/artunit)
