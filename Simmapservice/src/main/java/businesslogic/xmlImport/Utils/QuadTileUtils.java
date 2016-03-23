package businesslogic.xmlImport.Utils;

/**
 * Created by fke on 22.03.2016.
 */
public class QuadTileUtils {

    public static String getQuadTileKey(final int x, final int y, final int zoom) {
        StringBuilder quadKey = new StringBuilder();
        for (int i = zoom; i > 0; i--) {
            char digit = '0';
            int mask = 1 << (i - 1);
            if ((x & mask) != 0) {
                digit++;
            }
            if ((y & mask) != 0) {
                digit++;
                digit++;
            }
            quadKey.append(digit);
        }
        return quadKey.toString();
    }

    public static String getQuadTileKeyFromLatLong(final double lat, final double lon) {
        final int maxZoom = 18;
        return getQuadTileKey(getXTileNumber(lon, maxZoom), getYTileNumber(lat, maxZoom), maxZoom);
    }

    private static int getXTileNumber(final double lon, final int zoom) {
        int xtile = (int)Math.floor( (lon + 180) / 360 * (1<<zoom) ) ;
        if (xtile < 0)
            xtile=0;
        if (xtile >= (1<<zoom))
            xtile=((1<<zoom)-1);
        return xtile;
    }

    private static int getYTileNumber(final double lat, final int zoom) {
        int ytile = (int)Math.floor( (1 - Math.log(Math.tan(Math.toRadians(lat))
                + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1<<zoom) ) ;
        if (ytile < 0)
            ytile=0;
        if (ytile >= (1<<zoom))
            ytile=((1<<zoom)-1);
        return ytile;
    }

    public static String getMinCommonQuadTileKeyFromLatLong(final double fromLat, final double fromLon,
                                                            final double toLat, final double toLon) {
        return getMinCommonQuadTileKey(getQuadTileKeyFromLatLong(fromLat, fromLon),
                getQuadTileKeyFromLatLong(toLat, toLon));
    }

    public static String getMinCommonQuadTileKey(String from, String to) {
        if (from.equals(to)) {
            return from;
        }
        int minLength = Math.min(from.length(), to.length());
        for (int i = 0; i < minLength; i++) {
            if (from.charAt(i) != to.charAt(i)) {
                return from.substring(0, i);
            }
        }
        return "";
    }
}
