# -*- coding: utf-8 -*-

"""Generate a default configuration-file section for pk_genian_nac"""

from __future__ import print_function


def config_section_data():
    """Produce the default configuration section for app.config,
       when called by `resilient-circuits config [-c|-u]`
    """
    config_data = u"""[pk_genian_nac]
# genian_nac config
#nac_server=172.29.62.20
#api_key=912fae69-b454-4608-bf4b-fa142353b463
#tag_name=GUEST
"""
    return config_data
    # return None
