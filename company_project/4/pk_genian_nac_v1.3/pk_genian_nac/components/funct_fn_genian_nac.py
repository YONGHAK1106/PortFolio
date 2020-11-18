# -*- coding: utf-8 -*-
# pragma pylint: disable=unused-argument, no-self-use
"""Function implementation"""

import requests
import urllib
import json
import logging.handlers
import logging

from resilient_circuits import ResilientComponent, function, handler, StatusMessage, FunctionResult, FunctionError

class FunctionComponent(ResilientComponent):
    """Component that implements Resilient function 'fn_genian_nac"""

    def __init__(self, opts):
        """constructor provides access to the configuration options"""
        super(FunctionComponent, self).__init__(opts)
        self.options = opts.get("pk_genian_nac", {})

    @handler("reload")
    def _reload(self, event, opts):
        """Configuration options have changed, save new values"""
        self.options = opts.get("pk_genian_nac", {})

    @function("fn_genian_nac")
    def _fn_genian_nac_function(self, event, *args, **kwargs):
        """Function: None"""
        try:
           
            # Get the wf_instance_id of the workflow this Function was called in
            wf_instance_id = event.message["workflow_instance"]["workflow_instance_id"]

            # Get the function parameters:
            ip_address = kwargs.get("ip_address")  # text

            self.log = logging.getLogger(__name__)
            self.log.info("ip_address: %s", ip_address)

            # PUT YOUR FUNCTION IMPLEMENTATION CODE HERE
            #  yield StatusMessage("starting...")
            #  yield StatusMessage("done...")

            received_ip = ip_address
            
            #resilient_send_tag_period = str(tag_period) + "D" #day
            resilient_send_tag_period = "3D"

            ''' Input Genian NAC server IP (string) '''


            nac_info = {
                "nac_server" : self.options["nac_server"],
                "api_key" : self.options["api_key"]
                }
            
            # naming to tag(existed tagging is not need to mmake another policy) 
            assign_tag = {
                "tag_name" : self.options["tag_name"]
                }

            
            # JSON header
            header = {"accept" : "application/json", "content-type" : "application/json;charset=UTF-8"}

                        
            # check function 
            #self.log.info("{0} Connection Success".format(nac_info["nac_server"]))

            # Base URL
            URL = "http://"+nac_info["nac_server"]+"/mc2/rest/" 

            response = requests.get(URL + "nodes/"+received_ip+"/managementscope?apiKey="+nac_info["api_key"], verify=False)

            # Check exist node
            if response.status_code == 200:
                         
                # extract node_id from response 
                jsondata = response.json()
                node_id=jsondata[0]["nl_nodeid"]
                # print(node_id) 

                response1 = requests.get(URL + "tags?page=1&pageSize=30&apiKey="+nac_info["api_key"], verify=False)
                jsondata2 = response1.json()
                # print(jsondata2)

                if response1.status_code == 200:
                #    print("Retrieves a list of the tags. Check Success")
                    
                    tagid = "tag_no_exists"
                    
                    for a in jsondata2["result"]:
                        if a["NP_NAME"] == assign_tag["tag_name"]:
                            tagid = str(a["NP_IDX"])
                            print("[{0}] Tag exists in NAC. Tag Number : {1}".format(assign_tag["tag_name"], tagid))
                            break

                    # tag create start ...
                    if tagid == "tag_no_exists":
                        print("[{0}] Tag does not exist in NAC.".format(assign_tag["tag_name"]))
                        print("Adds a new tag start.....")
                        
                        tag1 = {"np_idx" : 0,
                            "np_name" : assign_tag["tag_name"],
                            "np_desc" : "",
                            "np_periodtype" : 0,
                            "np_period" : "string",
                            "np_periodexpire" : "string",
                            "np_adminroles" : "",
                            "np_color" : "string",
                            "np_static" : 0
                        }

                        data = json.dumps(tag1)
                        
                        #tag create api
                        tag_create = requests.post(URL + "tags?apiKey="+nac_info["api_key"], headers=header ,data=data, verify=False)
                        #print(tag_create.json())

                        response1 = requests.get(URL + "tags?page=1&pageSize=30&apiKey="+nac_info["api_key"], verify=False)
                        jsondata2 = response1.json()

                        for b in jsondata2["result"]:
                            if b["NP_NAME"] == assign_tag["tag_name"]:
                                tagid = str(b["NP_IDX"])
                                print("[{0}] Tag creation complete. Tag Number : {1}".format(assign_tag["tag_name"], tagid))
                                break

                    tag = [{
                        "id" : tagid,
                        "name" : "",
                        "description" : "",
                        "startDate" : "",
                        "expireDate" : "",
                        "periodType" : "period",
                        "expiryPeriod" : resilient_send_tag_period
                    }]

                    data1 = json.dumps(tag)
                    
                    print("----------------------------------Tag Assign Starting--------------------------------")
                    tagassign = requests.post(URL + "nodes/"+node_id+"/tags?apiKey="+nac_info["api_key"], headers=header, data=data1, verify=False)
                    print(tagassign.text)

                    if tagassign.status_code == 200:
                        print("[{0}] IP [{1}] Tag assign success.".format(received_ip, assign_tag["tag_name"]))
                    else:
                        jsondata3 = json.loads(tagassign.text.encode('utf-8'))
                        print('Response Code : ' + str(jsondata3["code"]) + ', Message : ' + jsondata3["message"])       
                else:
                    print("Response Code : {0}, Message : {1}".format(jsondata2["code"], jsondata2["message"]))
            else: 
                print("node ip "+received_ip+" is not exist in your network")

            #yield StatusMessage("_fn_genian_nac_function done...")

            results = {
                "value": "xyz"
            }

            # Produce a FunctionResult with the results
            #yield FunctionResult(results)

        except Exception:
            yield FunctionError()
