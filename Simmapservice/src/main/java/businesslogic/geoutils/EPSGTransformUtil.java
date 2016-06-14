package businesslogic.geoutils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

public class EPSGTransformUtil {
    private static final String WGS84 = "WGS84";
    private final CoordinateReferenceSystem sourceCrs;
    private final CoordinateReferenceSystem targetCrs;

    public EPSGTransformUtil(String sourceCrs) throws FactoryException {
        this(sourceCrs, WGS84);
    }

    public EPSGTransformUtil(String sourceCrs, String targetCrs) throws FactoryException {
        this.sourceCrs = CRS.decode(sourceCrs);
        this.targetCrs = targetCrs.equals(WGS84) ? DefaultGeographicCRS.WGS84 : CRS.decode(targetCrs);
    }

    public Geometry transform(Coordinate coord) throws TransformException, FactoryException {
        MathTransform transform = CRS.findMathTransform(sourceCrs, targetCrs);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Point point = geometryFactory.createPoint(coord);
        return JTS.transform(point, transform);
    }
}
