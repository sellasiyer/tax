package com.sn.services.tax;

import com.sn.services.tax.data.request.TaxData;
import com.sn.services.tax.data.response.TaxResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sellasiyer on 10/6/17.
 */
@RestController
public class TaxClientController {

    @Autowired
    private TaxService taxService;

    @RequestMapping(value = "/tax", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    TaxResponseData getTax(@RequestBody TaxData taxData) {

        return null;

    }

}
