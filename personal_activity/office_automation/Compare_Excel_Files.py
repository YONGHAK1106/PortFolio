# 출저 : https://hogni.tistory.com/89

import os
import time
from datetime import datetime
import pandas as pd
from openpyxl import load_workbook
import xlwings as xw


# 엑셀 디프 돌기위한 조건
# 1. 최초 베이스(old) 엑셀 파일은 파일명을 식별할수 있도록 old_date 변수를 설정해줘야함. (ex. 20211008_)
# 2. 비교를 위한 엑셀 파일은 "분석 번호" 행과열을 기준으로 수행되기 때문에, 위와 왼쪽 빈 줄은 자동으로 삭제하도록 처리되어 있음. (수동 삭제 하면 안됨.)
# 3. file_path 변수에 old 파일과 new 파일을 넣어놔야 함.


#########################################################################################################################


file_path = "C:\\Users\\KIMYONGHAK-PC\\Desktop\\report\\excel_comparison\\"
result_file_path = "C:\\Users\\KIMYONGHAK-PC\\Desktop\\report\\excel_comparison\\result\\"

# 파일 이름으로 비교하여 이름 변수 설정@@@@@@@@@@@@@@@@@@@@@@@@
#old_date = "20211008_"
src_webui = "src-webui"
src_c = "src-c_c++"
agentu = "agentu"
agent_osx = "agent_osx"
column_name = "분석 번호"


#########################################################################################################################


def compare_excel(old_xlsx, new_xlsx, column_name, file_name, version):

    ##########################################
    # 최초 리포트 다운로드시 나오는 열1,2 행1 삭제
    changes_xlsx = new_xlsx
    wb = load_workbook(changes_xlsx)
    ws = wb.active
    ws.delete_rows(1,2)
    ws.delete_cols(1)
    wb.save(changes_xlsx)
    print(changes_xlsx + " 열1,2 행1 삭제 완료")
    ##########################################

    df_old = pd.read_excel(old_xlsx)
    df_new = pd.read_excel(new_xlsx)

    # 불러온 데이터의 버전 구분
    df_old['ver'] = 'old'
    df_new['ver'] = 'new'

    id_dropped = set(df_old[column_name]) - set(df_new[column_name])
    id_added = set(df_new[column_name]) - set(df_old[column_name])

    # 삭제된 데이터
    df_dropped = df_old[df_old[column_name].isin(id_dropped)].iloc[:,:-1]
    # 추가된 데이터
    df_added = df_new[df_new[column_name].isin(id_added)].iloc[:,:-1]

    df_concatted = pd.concat([df_old, df_new], ignore_index=True)
    changes = df_concatted.drop_duplicates(df_concatted.columns[:-1], keep='last')
    duplicated_list = changes[changes[column_name].duplicated()][column_name].to_list()
    df_changed = changes[changes[column_name].isin(duplicated_list)]

    df_changed_old = df_changed[df_changed['ver'] == 'old'].iloc[:,:-1]
    df_changed_old.sort_values(by=column_name, inplace=True)

    df_changed_new = df_changed[df_changed['ver'] == 'new'].iloc[:,:-1]
    df_changed_new.sort_values(by=column_name, inplace=True)

    # 정보가 변경된 데이터 정리
    df_info_changed = df_changed_old.copy()
    for i in range(len(df_changed_new.index)):
        for j in range(len(df_changed_new.columns)):
            if (df_changed_new.iloc[i, j] != df_changed_old.iloc[i, j]):
                df_info_changed.iloc[i,j] = str(df_changed_old.iloc[i, j]) + " ==> " + str(df_changed_new.iloc[i,j])

    # 엑셀 저장            
    with pd.ExcelWriter(file_path + file_name + "_" + version + "compared_result.xlsx") as writer:
        #df_info_changed.to_excel(writer, sheet_name='info changed', index=False)
        df_added.to_excel(writer, sheet_name="added", index=False)
        df_dropped.to_excel(writer, sheet_name="dropped", index=False)  
        print("비교 파일 생성 완료 : " + file_name + "_" + version + "compared_result.xlsx\n")


#########################################################################################################################


def start(old_date, version):
    print("\n@@@@@@@@@@@@@@@@@@ OLD 파일과 NEW 파일 비교 시작 @@@@@@@@@@@@@@@@@@\n")
    old_list = []
    new_list = []

    files = os.listdir(file_path)

    for name in files:        
        if old_date in name and version+src_webui in name and (name.endswith(".xlsx") or name.endswith(".XLSX")):
            old_list.append(name)
        if old_date not in name and version+src_webui in name and (name.endswith(".xlsx") or name.endswith(".XLSX")):
            new_list.append(name)
        
        if old_date in name and version+src_c in name and (name.endswith(".xlsx") or name.endswith(".XLSX")):
            old_list.append(name)
        if old_date not in name and version+src_c in name and (name.endswith(".xlsx") or name.endswith(".XLSX")):
            new_list.append(name)

        if old_date in name and version+agentu in name and (name.endswith(".xlsx") or name.endswith(".XLSX")):
            old_list.append(name)
        if old_date not in name and version+agentu in name and (name.endswith(".xlsx") or name.endswith(".XLSX")):
            new_list.append(name)

        if old_date in name and version+agent_osx in name and (name.endswith(".xlsx") or name.endswith(".XLSX")):
            old_list.append(name)
        if old_date not in name and version+agent_osx in name and (name.endswith(".xlsx") or name.endswith(".XLSX")):
            new_list.append(name)


    date = datetime.today().strftime("%Y%m%d_")
    
    for old_file in old_list:
        for new_file in new_list:
            if version+src_webui in old_file and version+src_webui in new_file and (".XLSX" in name or ".xlsx" in name):
                old_xlsx = file_path + old_file
                new_xlsx = file_path + new_file
                print("OLD 파일 : " + old_xlsx + "\n" + "NEW 파일 : " + new_xlsx)
                compare_excel(old_xlsx, new_xlsx, column_name, date + src_webui, version)

            if version+src_c in old_file and version+src_c in new_file and (".XLSX" in name or ".xlsx" in name):
                old_xlsx = file_path + old_file
                new_xlsx = file_path + new_file
                print("OLD 파일 : " + old_xlsx + "\n" + "NEW 파일 : " + new_xlsx)
                compare_excel(old_xlsx, new_xlsx, column_name, date + src_c, version)

            if version+agentu in old_file and version+agentu in new_file and (".XLSX" in name or ".xlsx" in name):
                old_xlsx = file_path + old_file
                new_xlsx = file_path + new_file
                print("OLD 파일 : " + old_xlsx + "\n" + "NEW 파일 : " + new_xlsx)
                compare_excel(old_xlsx, new_xlsx, column_name, date + agentu, version)

            if version+agent_osx in old_file and version+agent_osx in new_file and (".XLSX" in name or ".xlsx" in name):
                old_xlsx = file_path + old_file
                new_xlsx = file_path + new_file
                print("OLD 파일 : " + old_xlsx + "\n" + "NEW 파일 : " + new_xlsx)
                compare_excel(old_xlsx, new_xlsx, column_name, date + agent_osx, version)
    print("\n@@@@@@@@@@@@@@@@@@ OLD 파일과 NEW 파일 비교 종료 @@@@@@@@@@@@@@@@@@\n")

    time.sleep(3)

    print("\n@@@@@@@@@@@@@@@@@@ 엑셀파일 암호화 시작 @@@@@@@@@@@@@@@@@@\n")
    # 파일 암호화
    files = os.listdir(file_path)
    for name in files:
        # 파일 풀 경로
        full_File_Name = file_path + name

        # Excel 파일 암호화
        if (src_webui in name or src_c in name or agentu in name or agent_osx in name) and version + "compared_result" in name and (name.endswith(".xlsx") or name.endswith(".XLSX")):
            #print(os.getcwd())
            book = xw.Book(full_File_Name)
            book.api.SaveAs(full_File_Name, Password='wjarjarufrhk')
            print("파일 암호화 성공 : " + name)
    print("\n@@@@@@@@@@@@@@@@@@ 엑셀파일 암호화 종료 @@@@@@@@@@@@@@@@@@\n")


#########################################################################################################################

        
if __name__=="__main__":
    start("20220218", "RC_")
    start("20220218", "RELEASE_")
    start("20211213", "LTS_")

#    compare_excel("C:\\Users\\KIMYONGHAK-PC\\Desktop\\report\\excel_comparison\\20211008_REPORT_RAW_DATA-RC_agent_osx.XLSX", 
#    "C:\\Users\\KIMYONGHAK-PC\\Desktop\\report\\excel_comparison\\REPORT_RAW_DATA_NS-RC_agent_osx.XLSX", "분석 번호")
