package kr.co.cardledger;

import android.app.ActionBar;
import android.os.Bundle;

/**
 * @desc : 메인 탭 엑티비티로 탭메뉴형태로 각 메뉴별로 프레그먼트로
 * 	구성되어 있다.
 */
public class MainTabsActivity extends MyActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,	WindowManager.LayoutParams.FLAG_FULLSCREEN);
		final ActionBar actionBar = getActionBar();
		// 타이틀 바 없애기
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);		
		// tab 모드로
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar
				.newTab()
				.setText("이달내역")
				.setTabListener(
						new TabListener<TotalSumFragment>(this, "tab1",
								TotalSumFragment.class)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("전체")
				.setTabListener(
						new TabListener<AllListFragment>(this, "tab2",
								AllListFragment.class)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("월별")
				.setTabListener(
						new TabListener<MonthListFragment>(this, "tab3",
								MonthListFragment.class)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("카드사별")
				.setTabListener(
						new TabListener<CompanyListFragment>(this, "tab4",
								CompanyListFragment.class)));
		actionBar.addTab(actionBar
				.newTab()
				.setText("지도")
				.setTabListener(
						new TabListener<MapFragment>(this, "tab5",
								MapFragment.class)));

		actionBar.addTab(actionBar
				.newTab()
				.setText("환경설정")
				.setTabListener(
						new TabListener<SettingFragment>(this, "tab6",
								SettingFragment.class)));

		if (savedInstanceState != null) {
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt(
					"selectedTab", 0));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("selectedTab", getActionBar()
				.getSelectedNavigationIndex());
	}
}