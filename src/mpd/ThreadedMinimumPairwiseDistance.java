package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    private long globalResult = Integer.MAX_VALUE;
    private int[] list;
    private int n;
    

    @Override
    public long minimumPairwiseDistance(int[] values) {

        list = values;
        n = values.length;

        ThreadedMinimumPairwiseDistance.lowerLeft ll = new ThreadedMinimumPairwiseDistance().new lowerLeft(0,0,0,n/2);
        ThreadedMinimumPairwiseDistance.lowerRight lr = new ThreadedMinimumPairwiseDistance().new lowerRight(n/2,n/2,0,n);
        ThreadedMinimumPairwiseDistance.center c = new ThreadedMinimumPairwiseDistance().new center(n/2,n/2,0,n);
        ThreadedMinimumPairwiseDistance.upperRight ur = new ThreadedMinimumPairwiseDistance().new upperRight(n/2,0,0,n);

        Thread llThread = new Thread(ll);
        llThread.start();

        Thread lrThread = new Thread(lr);
        lrThread.start();

        Thread cThread = new Thread(c);
        cThread.start();

        Thread urThread = new Thread(ur);
        urThread.start();

        try {
            
                llThread.join();
                lrThread.join();
                cThread.join();
                urThread.join();
            
        } catch (InterruptedException exc){
            System.out.println("Thread interrupted");
        }

        return globalResult;
    }

    public void updateGlobalResult(long localResult){
        if(localResult < globalResult){
            globalResult = localResult;
        }
    }

    private class lowerLeft implements Runnable{

        private int begin;
        private int jBound;
        private int iBound;
        private int end;

        public lowerLeft(int begin, int jBound, int iBound, int end){
            this.begin = begin;
            this.jBound = jBound;
            this.iBound = iBound;
            this.end = end;
        }
        
        @Override
        public void run() {
            long localResult = Integer.MAX_VALUE;
        for (int i = begin; i+iBound < end; ++i) {
            for (int j = begin; j+jBound < i; ++j) {
                // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                long diff = Math.abs(list[i] - list[j]);
                if (diff < localResult) {
                    localResult = diff;
                }
            }
        }
            System.out.println(localResult);
            updateGlobalResult(localResult);
        }
    }
    private class lowerRight implements Runnable{

        private int begin;
        private int jBound;
        private int iBound;
        private int end;

        public lowerRight(int begin, int jBound, int iBound, int end){
            this.begin = begin;
            this.jBound = jBound;
            this.iBound = iBound;
            this.end = end;
        }
        
        @Override
        public void run() {
            long localResult = Integer.MAX_VALUE;
            for (int i = begin; i+iBound < end; ++i) {
                for (int j = begin; j+jBound < i; ++j) {
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(list[i] - list[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }
            System.out.println(localResult);
            updateGlobalResult(localResult);
        }
    }
    private class center implements Runnable{
        
        private int begin;
        private int jBound;
        private int iBound;
        private int end;

        private center(int begin, int jBound, int iBound, int end){
            this.begin = begin;
            this.jBound = jBound;
            this.iBound = iBound;
            this.end = end;
        }

        @Override
        public void run() {
            long localResult = Integer.MAX_VALUE;
            for (int j = begin; j+jBound < end; ++j) {
                for (int i = begin; i+iBound < j; ++i) {
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(list[i] - list[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }
            System.out.println(localResult);
            updateGlobalResult(localResult);
        }
    }
    private class upperRight implements Runnable{
       
        private int begin;
        private int jBound;
        private int iBound;
        private int end;

        private upperRight(int begin, int jBound, int iBound, int end){
            this.begin = begin;
            this.jBound = jBound;
            this.iBound = iBound;
            this.end = end;
        }

        @Override
        public void run() {
            long localResult = Integer.MAX_VALUE;
            for (int i = begin; i+iBound < end; ++i) {
                for (int j = begin; j+jBound < i; ++j) {
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(list[i] - list[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }
            System.out.println(localResult);
            updateGlobalResult(localResult);
        }
    }
}
