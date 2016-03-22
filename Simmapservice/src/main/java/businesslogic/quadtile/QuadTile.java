package businesslogic.quadtile;

/**
 * Created by fke on 21.03.2016.
 */
public class QuadTile {
    public final int x;
    public final int y;
    public final int zoom;

    public QuadTile(final int x, final int y, int zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    public String generateQuadTileKey() {
        StringBuilder quadKey = new StringBuilder();
        for (int i = this.zoom; i > 0; i--) {
            char digit = '0';
            int mask = 1 << (i - 1);
            if ((this.x & mask) != 0) {
                digit++;
            }
            if ((this.y & mask) != 0) {
                digit++;
                digit++;
            }
            quadKey.append(digit);
        }
        return quadKey.toString();
    }

    public static int getXTileNumber(final double lon, final int zoom) {
        int xtile = (int)Math.floor( (lon + 180) / 360 * (1<<zoom) ) ;
        if (xtile < 0)
            xtile=0;
        if (xtile >= (1<<zoom))
            xtile=((1<<zoom)-1);
        return xtile;
    }

    public static int getYTileNumber(final double lat, final int zoom) {
        int ytile = (int)Math.floor( (1 - Math.log(Math.tan(Math.toRadians(lat))
                + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1<<zoom) ) ;
        if (ytile < 0)
            ytile=0;
        if (ytile >= (1<<zoom))
            ytile=((1<<zoom)-1);
        return ytile;
    }

    public static String getMaxQuadTileKey(String from, String to) {
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
