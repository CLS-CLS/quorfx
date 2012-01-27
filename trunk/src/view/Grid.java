
package view;

/**
 *
 * @author lytsikas
 */
public interface Grid {

    int getCol();

    int getRow();

    void setCol(int col);

    /**
     * Convenience method
     * @param row
     * @param col
     */
    void setPosition(int row, int col);

    void setRow(int row);
    
}
