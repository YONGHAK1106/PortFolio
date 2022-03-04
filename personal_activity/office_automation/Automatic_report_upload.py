# 파일 이름 수정
# pdf 파일 암호화
# jira 연동
# jira 에 파일 자동 업로드 및 코멘트 자동 작성

''' IMPORT '''

import os
from datetime import datetime
import time
import xlwings as xw
from pikepdf import Pdf
import pikepdf
import PyPDF2
from jira import JIRA

# 참고
# 지라 : https://jira.readthedocs.io/index.html
# PDF 파일 암호화 : https://readthedocs.org/projects/pikepdf/downloads/pdf/latest/
# pikepdf docs : https://pikepdf.readthedocs.io/en/latest/topics/outlines.html


''' PARAMS '''

# JIRA URL 및 계정 정보
options = {"server": "https://ims.genians.com/jira/"}
jira = JIRA(options, basic_auth=('yhkim93', '@@QQyhkim!!!4488'))

# 시간 계산
now = time.localtime()
week = ('월', '화', '수', '목', '금', '토', '일' )
date = datetime.today().strftime("%Y. %m. %d. ")


''' HELPER FUNCTIONS '''

# 파일 이름에 날짜 붙여서 수정
def change_file_name(file_path):
    files = os.listdir(file_path)
    for name in files:
        src = os.path.join(file_path, name)
        dst = datetime.today().strftime("%Y%m%d") + "_" + name
        dst = os.path.join(file_path, dst)
        os.rename(src, dst.replace("[","_").replace("]","_")) # 엑셀에 "[" , "]" 가 들어가면 암호화에서 오류가 생김.
        print("파일 이름 변경 성공 : " + dst)
        

# 파일 암호화
def file_decryption(file_path):
    files = os.listdir(file_path)
    for name in files :
        # 파일 풀 경로
        full_File_Name = file_path + "\\" + name

        # pdf 파일 암호화
        if name.endswith(".pdf") or name.endswith(".PDF"):
            # print(i)
            with Pdf.open(full_File_Name, allow_overwriting_input=True) as pdf:
                no_extracting = pikepdf.Permissions(extract=False)
                pdf.save(full_File_Name, encryption=pikepdf.Encryption(user="wjarjarufrhk", owner="wjarjarufrhk", allow=no_extracting))
                print("파일 암호화 성공 : " + name)

        # Excel 파일 암호화
        if name.endswith(".xlsx") or name.endswith(".XLSX"):
            print(os.getcwd())
            book = xw.Book(full_File_Name)
            book.api.SaveAs(full_File_Name, Password='wjarjarufrhk')
            print("파일 암호화 성공 : " + name)




#########################################################################
#########################################################################
"""취약점 점검"""


# 취약점 점검
def nessus_nac(file_path, version, issue_num, release_name):
    issue = jira.issue(issue_num)
    # description = issue.fields.description
    
    """여기부터 Comment 작성을 위한 코드"""
    files = os.listdir(file_path)
    
    pdf_list = []

    for name in files:
        # 파일 풀 경로
        full_File_Name = file_path + "\\" + name
        
        if release_name == "NAC_V5_0_Release_-" and "NAC_V5_0_Release_-" in name and (".PDF" in name or ".pdf" in name):
            # 지라에 파일 추가
            jira.add_attachment(issue=issue, attachment=full_File_Name)
            print("파일 지라에 업로드 완료 : " + name)
            
            pdf_list.append(name)

        if release_name == "NAC_V5_0_Release_Candidate" and "NAC_V5_0_Release_Candidate" in name and (".PDF" in name or ".pdf" in name):
            # 지라에 파일 추가
            jira.add_attachment(issue=issue, attachment=full_File_Name)
            print("파일 지라에 업로드 완료 : " + name)
            
            pdf_list.append(name)

        if release_name == "NAC_V5_0_42_LTS" and "NAC_V5_0_42_LTS" in name and (".PDF" in name or ".pdf" in name):
            # 지라에 파일 추가
            jira.add_attachment(issue=issue, attachment=full_File_Name)
            print("파일 지라에 업로드 완료 : " + name)
            
            pdf_list.append(name)

        if release_name == "NAC_V4_0_1_Release" and "NAC_V4_0_1_Release" in name and (".PDF" in name or ".pdf" in name):
            # 지라에 파일 추가
            jira.add_attachment(issue=issue, attachment=full_File_Name)
            print("파일 지라에 업로드 완료 : " + name)
            
            pdf_list.append(name)

        if release_name == "I_E_V2_0_100_RC" and "I_E_V2_0_100_RC_" in name and (".PDF" in name or ".pdf" in name):
            # 지라에 파일 추가
            jira.add_attachment(issue=issue, attachment=full_File_Name)
            print("파일 지라에 업로드 완료 : " + name)
            
            pdf_list.append(name)

        if release_name == "GPI_V4_0_Release" and "GPI_V4_0_Release_" in name and (".PDF" in name or ".pdf" in name):
            # 지라에 파일 추가
            jira.add_attachment(issue=issue, attachment=full_File_Name)
            print("파일 지라에 업로드 완료 : " + name)
            
            pdf_list.append(name)

    comment = """
{0} {1}
* *2021. 12. 13. 월 리포트 내용과 동일*
* 점검 버전 : {2}
* 취약점 점검 수행 리포트 : [^{3}]
    """.format(date, week[now.tm_wday], version, pdf_list[0])

    jira.add_comment(issue , comment)
    print("지라에 코멘트 작성 완료\n" + comment)


    """여기부터 Description 업데이트를 위한 코드"""
    description = issue.fields.description

    # pdf 파싱을 위해 잠시 암호화 해제
    with pikepdf.open(file_path + "\\" + pdf_list[0], password='wjarjarufrhk') as pdf:
        pdf_rename = file_path + "\\tmp.pdf"
        pdf.save(pdf_rename) #암호화 해제된 상태로 저장됨.

    with open(pdf_rename, "rb") as pdfFileObject:
        pdfReader = PyPDF2.PdfFileReader(pdfFileObject)

        page = pdfReader.getPage(3) #Nessus pdf 3번째 페이지에 critical, high, medium, low, info 정보가 있음.
        # print(page.extractText())
        
        txt_rename = file_path + "\\tmp.txt"

        with open(txt_rename, "w") as w:
            w.write(page.extractText())

        with open(txt_rename, 'r') as r:
            r.readline()
            critical = r.readline().strip() #strip() : 공백제거
            high = r.readline().strip()
            medium = r.readline().strip()
            low = r.readline().strip()
            info = r.readline().strip()
            # print("critical : " + critical + " / high : " + high + " / medium : " + medium + " / low : " + low + " / info : " + info )
        
    # 정보만 저장했기때문에 이제 삭제
    os.system("del " + txt_rename)
    os.system("del " + pdf_rename)

    description_add = "| {0}{1} | {2} || {3} || {4} || {5} || {6} || {7} ||\n".format(date, week[now.tm_wday], version, critical, high, medium, low, info)
    description_add_2 = "h2. *점검결과 - 7주차 (2/7)*\n(최초 점검결과에서 추가 혹은 제거된 내용만 표시)\n* 2021. 12. 13. 월 리포트 내용과 동일\n\n"

    index = description.find("\nh2. *참고*")
    final_string = description[:index] + description_add + description_add_2 + description[index:]

    issue.update(fields={'description': final_string})
    print("\n지라에 description 업데이트 완료\n" + description_add)


#########################################################################
#########################################################################
"""시큐어코딩"""

# 시큐어코딩 NAC V5.0 RELEASE
def secure_nac_v50_release(file_path, issue_num):
    issue = jira.issue(issue_num)
    # description = issue.fields.description
    
    files = os.listdir(file_path)
    
    pdf_list = []
    excel_list = []

    for name in files:
        # 파일 풀 경로
        full_File_Name = file_path + "\\" + name
        
        if "REPORT_DETAIL_NS-RELEASE" in name and (".PDF" in name or ".pdf" in name):
            # 지라에 파일 추가
            jira.add_attachment(issue=issue, attachment=full_File_Name)
            print("파일 지라에 업로드 완료 : " + name)
            
            pdf_list.append(name)

        if "REPORT_RAW_DATA_NS-RELEASE" in name and (".XLSX" in name or ".xlsx" in name):
            # 지라에 파일 추가
            jira.add_attachment(issue=issue, attachment=full_File_Name)
            print("파일 지라에 업로드 완료 : " + name)
            
            excel_list.append(name)

    comment = """
{0} {1}
* PDF 보고서 : 보안약점별 패치 가이드 (샘플 코드 제공)
** [^{2}]
** [^{3}]
** [^{4}]
** [^{5}]
** [^{6}]
** [^{7}]

* 엑셀상세 자료 : 보안약점 탐지결과 및 원인 코드 정보
** [^{8}]
** [^{9}]
** [^{10}]
** [^{11}]
** [^{12}]
** [^{13}]
    """.format(date, week[now.tm_wday], 
    pdf_list[0], pdf_list[1], pdf_list[2], pdf_list[3], pdf_list[4], pdf_list[5], 
    excel_list[0], excel_list[1], excel_list[2], excel_list[3], excel_list[4], excel_list[5])

    jira.add_comment(issue , comment)
    print("지라에 코멘트 작성 완료\n" + comment)
