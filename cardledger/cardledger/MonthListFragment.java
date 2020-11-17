package kr.co.cardledger;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 월별 내역 조회
 */
public class MonthListFragment extends Fragment {
	private ArrayList<CardData> list;
	private ListAdapter la;
	private Context mContext;
	private View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
		rootView = inflater.inflate(R.layout.month_list_layout, container, false);
		super.onCreate(savedInstanceState);
		// list = getList();
		final ListView lv = (ListView)rootView.findViewById(R.id.list);
		final TextView countTv = (TextView)rootView.findViewById(R.id.list_count);
		// 카드사를 선택할 스피너 설정
		Spinner spinner = (Spinner)rootView.findViewById(R.id.company_spinner);
		int month = MyActivity.calenda.get(Calendar.MONTH) + 1;
		CharSequence[] arr = new CharSequence[month];
		for (int i = 0; i < month; i++) {
			arr[i] = String.format("%02d", (i + 1)) + "월";
		}

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(mContext, android.R.layout.simple_spinner_item, arr);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(month - 1);
		// 카드사를 선택시 해당 선택 카드사 내역 보여주기
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> parent,
					final View v, final int pos, final long arg3) {

				String card = (String) parent.getItemAtPosition(pos);
				list = getList(card);
				if (list != null) {
					la = new ListAdapter(mContext, list, R.layout.list_item_layout);
					lv.setAdapter(la);
					countTv.setText("사용 내역 건수 : " + list.size());
				} else {
					Toast.makeText(mContext, "사용내역이 없습니다.", 	Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onNothingSelected(final AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		// 아이템 선택시 세부내역 다이얼로그를 보여준다.
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View arg1, final int pos, final long arg3) {
				// TODO Auto-generated method stub
				ListAdapter adapter = (ListAdapter) parent.getAdapter();
				CardData c = (CardData) adapter.getItem(pos);
				detailDialog(c);
			}
		});

		return rootView;
	}

	/**
	 * 월별벼 사용내역을 리스트에 담는다.
	 * @return 내역 ArrayList 리스트 객체
	 */
	private ArrayList<CardData> getList(final String month) {
		// db에서 카드 sms 내역을 불러온다.
		SQLiteOpenHelper dbhelper = new DBHelper(mContext);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.CARD_TABLE, null,
				"substr(date, 1, 2) = ?",
				new String[] { month.replace("월", ""), }, null, null,
				"date desc");
		ArrayList<CardData> list = null; // 카드 사용내역을 담을 리스트
		CardData data = null;
		list = new ArrayList<CardData>();
		if (cursor.moveToFirst()) { // 내역이 있으면
			do {
				data = new CardData();
				data.setCardCompany(cursor.getString(cursor
						.getColumnIndex("company")));
				data.setPrice(cursor.getString(cursor.getColumnIndex("price")));
				data.setShop(cursor.getString(cursor.getColumnIndex("shop")));
				data.setBuy(cursor.getInt(cursor.getColumnIndex("buy")));
				data.setDate(cursor.getString(cursor.getColumnIndex("date")));
				list.add(data); // 카드내역 데이터 추가
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		dbhelper.close();
		return list;

	}

	/**
	 * 세부내역 다이얼로그
	 * 
	 * @param c
	 *            내역정보 객체
	 */
	private void detailDialog(final CardData c) {
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.detail_info);
		dialog.setTitle("사용내역");
		// 다이얼로그 후킹
		((TextView) dialog.findViewById(R.id.card_name)).setText(c
				.getCardCompany());
		((TextView) dialog.findViewById(R.id.card_date)).setText(c.getDate());
		((TextView) dialog.findViewById(R.id.card_shop)).setText(c.getShop());
		((TextView) dialog.findViewById(R.id.card_price)).setText(c.getPrice());

		dialog.show();
	}

}
