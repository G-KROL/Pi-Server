package pl.edu.agh.eaiib.web.relay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.eaiib.relay.LightBo;


@RestController
@RequestMapping("/lightrelay")
public class LightRelayController {

    private LightBo bo;

    @Autowired
    public LightRelayController(LightBo bo) {
        this.bo = bo;
    }

    @RequestMapping(method = RequestMethod.GET)
    public RelayResultDto state() {
        return new RelayResultDto(bo.state());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RelayResultDto change(@RequestBody RelayResultDto resultDto) {
        return new RelayResultDto(bo.change(resultDto.getRelayState()));
    }

}
