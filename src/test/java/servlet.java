import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MaintainerDao;
import domain.Maintainer;
import org.junit.Test;
import service.MaintainerService;

import java.util.HashMap;

public class servlet {
    MaintainerService ms = new MaintainerService();
    @Test
    public void testLoginJson() throws JsonProcessingException {
        Maintainer mtnr = ms.loginMaintainer("17623697924","000000");


        //用map去做json
        HashMap<String, Object> json = new HashMap<>();
        json.put("success",true);
        json.put("engineerData", mtnr);
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(json);
        System.out.println(jsonStr);
        //用json去做json
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",true);
        jsonObject.put("engineerData", mtnr);
        System.out.println(jsonObject.toJSONString());
    }

}
