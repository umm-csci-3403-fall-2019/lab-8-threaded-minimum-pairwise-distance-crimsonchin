package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    private static long globalResult = Integer.MAX_VALUE;
    private static int[] list;
    private int n;
    

    @Override
    public long minimumPairwiseDistance(int[] values) {

        this.list = values;
        n = values.length;

        ThreadedMinimumPairwiseDistance.lowerLeft ll = new ThreadedMinimumPairwiseDistance().new lowerLeft(0,0,0,n/2);
        ThreadedMinimumPairwiseDistance.lowerRight lr = new ThreadedMinimumPairwiseDistance().new lowerRight(n/2,n/2,0,n);
        ThreadedMinimumPairwiseDistance.center c = new ThreadedMinimumPairwiseDistance().new center(n/2,n/2,0,n);
        ThreadedMinimumPairwiseDistance.upperRight ur = new ThreadedMinimumPairwiseDistance().new upperRight(n/2,0,0,n);

        if(n>0){
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
    }
        long temp = this.globalResult;
        this.globalResult = Integer.MAX_VALUE;
        return temp;
    }

    public void updateGlobalResult(long localResult){
        if(localResult < this.globalResult){
            this.globalResult = localResult;
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
                // System.out.print(list[i]+ " " + list[j]);
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
                for (int j = 0; j+jBound < i; ++j) {
                    // System.out.print(list[i]+ " " + list[j]);
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(list[i] - list[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }
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
            for (int i = begin; i+iBound < end; ++i) {
                for (int j = 0; j+jBound > i; ++j) {
                    // System.out.print(list[i]+ " " + list[j]);
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(list[i] - list[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }
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
                for (int j = 0; j+jBound < i; ++j) {
                    // System.out.print(list[i]+ " " + list[j]);
                    // Gives us all the pairs (i, j) where 0 <= j < i < values.length
                    long diff = Math.abs(list[i] - list[j]);
                    if (diff < localResult) {
                        localResult = diff;
                    }
                }
            }
            updateGlobalResult(localResult);
        }
    }
}
