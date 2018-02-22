import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20170112.ImageSyncScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.junit.Test;

import java.util.*;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/2/22 11:01
 */
public class OcrTest extends BaseJunit4Test {

    @Test
    public void test01() throws Exception {
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAIQUej4oxXxRmS", "ssPxZMk7dl1pzjr2L641pOIF3Cgbw7");
        DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Green", "green.cn-shanghai.aliyuncs.com");
        IAcsClient client = new DefaultAcsClient(profile);
        ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
        imageSyncScanRequest.setAcceptFormat(FormatType.JSON); // 指定api返回格式
        imageSyncScanRequest.setContentType(FormatType.JSON);
        imageSyncScanRequest.setMethod(com.aliyuncs.http.MethodType.POST); // 指定请求方法
        imageSyncScanRequest.setEncoding("utf-8");
        imageSyncScanRequest.setRegionId("cn-shanghai");
        List<Map<String, Object>> tasks = new ArrayList<>();
        Map<String, Object> task = new LinkedHashMap<>();
        task.put("dataId", UUID.randomUUID().toString());
        task.put("url",
                "http://cdn.aegisa.cn/256.png");
        task.put("time", new Date());
        tasks.add(task);
        JSONObject data = new JSONObject();
        /**
         * porn: 色情
         * terrorism: 暴恐
         * qrcode: 二维码
         * ad: 图片广告
         * ocr: 文字识别
         */
        data.put("scenes", Collections.singletonList("ocr"));
        data.put("tasks", tasks);
        imageSyncScanRequest.setContent(data.toJSONString().getBytes("UTF-8"), "UTF-8", FormatType.JSON);
        /**
         * 请务必设置超时时间
         */
        imageSyncScanRequest.setConnectTimeout(3000);
        imageSyncScanRequest.setReadTimeout(6000);
        try {
            HttpResponse httpResponse = client.doAction(imageSyncScanRequest);
            if (httpResponse.isSuccess()) {
                JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getContent(), "UTF-8"));
                System.out.println(JSON.toJSONString(scrResponse, true));
                if (200 == scrResponse.getInteger("code")) {
                    JSONArray taskResults = scrResponse.getJSONArray("data");
                    for (Object taskResult : taskResults) {
                        if (200 == ((JSONObject) taskResult).getInteger("code")) {
                            JSONArray sceneResults = ((JSONObject) taskResult).getJSONArray("results");
                            for (Object sceneResult : sceneResults) {
                                String scene = ((JSONObject) sceneResult).getString("scene");
                                String suggestion = ((JSONObject) sceneResult).getString("suggestion");
                                //根据scene和suggetion做相关的处理
                                //do something
                                System.out.println("args = [" + scene + "]");
                                System.out.println("args = [" + suggestion + "]");
                            }
                        } else {
                            System.out.println("task process fail:" + ((JSONObject) taskResult).getInteger("code"));
                        }
                    }
                } else {
                    System.out.println("detect not success. code:" + scrResponse.getInteger("code"));
                }
            } else {
                System.out.println("response not success. status:" + httpResponse.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
