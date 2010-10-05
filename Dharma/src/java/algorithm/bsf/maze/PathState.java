package algorithm.bsf.maze;

/**
 * @author Fan Hongtao
 * @created 2009-3-14
 */
class PathState
{
    private PathState()
    {
    }
    
    /**
     * 道路畅通，可以行走
     */
    public static final int EMPTY = 0;
    
    /**
     * 道路阻塞，不能行走
     */
    public static final int BLOCK = 1;
}
