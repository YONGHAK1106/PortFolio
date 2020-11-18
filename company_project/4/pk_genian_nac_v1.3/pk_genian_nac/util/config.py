# -*- coding: utf-8 -*-

"""Generate a default configuration-file section for pk_genian_nac"""

from __future__ import print_function


def config_section_data():
    """Produce the default configuration section for app.config,
       when called by `resilient-circuits config [-c|-u]`
    """
    config_data = u"""[pk_genian_nac]

# Input Genian NAC config 
nac_server=172.29.52.220
api_key=12345678-1234-1234-1234-123456789abc
tag_name=Resilient_enforcement
"""
    return config_data
    # return None
