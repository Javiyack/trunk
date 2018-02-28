package utilities;

import java.util.Arrays;

public class Statistics {
	Object[] data;
    int size;   

    public Statistics(Object[] data) {
        this.data = data;
        size = data.length;
    }   

    public double getMean() {
        double sum = 0.0;
        for(Object a : data)
            sum += (long) a;
        return sum/size;
    }

    public double getVariance() {
        double mean = getMean();
        double temp = 0;
        for(Object a :data)
            temp += ((long)a-mean)*((long)a-mean);
        
        if (size-1 !=0)
        	return temp/(size-1);
        else
        	return 0;
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }

    public double median() {
       Arrays.sort(data);

       if (data.length % 2 == 0) {
          return ((long) data[(data.length / 2) - 1] + (long) data[data.length / 2]) / 2.0;
       } 
       return (long) data[data.length / 2];
    }
}
