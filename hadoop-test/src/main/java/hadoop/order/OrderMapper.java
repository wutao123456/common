package hadoop.order;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author wutao
 * @email wutao56789@gmail.com
 * @date 2019/5/26 22:50
 */
public class OrderMapper extends Mapper<LongWritable, Text,OrderBean, NullWritable> {


    OrderBean k = new OrderBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String[] fields = line.split(" ");

        k.setOrder_id(Integer.parseInt(fields[0]));
        k.setPrice(Double.parseDouble(fields[2]));
        context.write(k,NullWritable.get());
    }
}
