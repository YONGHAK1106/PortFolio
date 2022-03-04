import method as m

"""파라미터"""

# 파일 경로
file_path = 'D:\\ftp_yonghak\\python_test'

######################################################################################

# 이슈 정보 (취약점 점검)
nessus_nac_v50_r = "GN-24516"
nessus_nac_v50_rc = "GN-24518"
nessus_nac_v50_lts = "GN-23888"
nessus_nac_v40_r = "GN-24522"
nessus_edr_v20_rc = "GS-5704"
nessus_gpi_v40_r = "CP-5409"

# 취약점 점검 버전
nessus_nac_v50_r_ver = "NAC-CT64-R-102972-5.0.45.0303.img"
nessus_nac_v50_rc_ver = "NAC-CT64-R-102971-5.0.46.0303.img"
nessus_nac_v50_lts_ver = "NAC-CT64-R-102942-5.0.42.0302.img"
nessus_nac_v40_r_ver = "NAC-CT64-R-102937-4.0.148.0302.img"
nessus_edr_v20_rc_ver = "INSIGHTS-CT64-R-25287-2.0.101.1209.img"
nessus_gpi_v40_r_ver = "GPI-CT64-RELEASE-25390-4.0.15.1209.img"

######################################################################################

# 이슈 정보 (시큐어코딩)
secure_nac_v50_r = "GN-24517"
secure_nac_v50_rc = "GN-24519"
#secure_nac_v50_rc = "GE-4065" #테스트 개인 이슈
secure_nac_v50_lts = "GN-23889"
secure_edr_v20_b = "GE-4227"
secure_edr_v20_rc = "GS-5705"
secure_gpi_v40_r = "CP-5410"

######################################################################################

# 테스트 이슈 정보
issue_test = 'GE-4065'

######################################################################################

def first():
    # 파일 이름 변경
    m.change_file_name(file_path)

    # 파일 암호화
    m.file_decryption(file_path)

def jiraCommentAdd_Nessus():
    print()
    # 취약점 점검 리포트 지라 코멘트에 업로드 메소드

    m.nessus_nac(file_path, nessus_nac_v50_r_ver, nessus_nac_v50_r, "NAC_V5_0_Release_-")
    m.nessus_nac(file_path, nessus_nac_v50_rc_ver, nessus_nac_v50_rc, "NAC_V5_0_Release_Candidate")
    m.nessus_nac(file_path, nessus_nac_v50_lts_ver, nessus_nac_v50_lts, "NAC_V5_0_42_LTS")
    m.nessus_nac(file_path, nessus_nac_v40_r_ver, nessus_nac_v40_r, "NAC_V4_0_1_Release")
    
    m.nessus_nac(file_path, nessus_edr_v20_rc_ver, nessus_edr_v20_rc, "I_E_V2_0_100_RC")
    m.nessus_nac(file_path, nessus_gpi_v40_r_ver, nessus_gpi_v40_r, "GPI_V4_0_Release")

def jiraCommentAdd_SecureCoding():
    print()
    # 시큐어코딩 리포트 지라 코멘트에 업로드 메소드
    
    m.secure_nac_v50_release(file_path, secure_nac_v50_r)
    m.secure_nac_v50_release_cadidate(file_path, secure_nac_v50_rc)
    m.secure_nac_v50_lts(file_path, secure_nac_v50_lts)
    
    m.secure_edr_v20_beta(file_path, secure_edr_v20_b)
    m.secure_edr_v20_release_candidate(file_path, secure_edr_v20_rc)
    m.secure_gpi_v40_release(file_path, secure_gpi_v40_r)

if __name__=="__main__":
    print("main 함수 시작")

    first()
    jiraCommentAdd_Nessus()
    jiraCommentAdd_SecureCoding()

    print("main 함수 종료")
