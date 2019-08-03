package com.qlib.components;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.qlib.R;
import com.qlib.base.BaseActivity;
import com.qlib.qutils.QUtils;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class QPayPasswordDialog {
	private AlertDialog myDialog = null;
	private GridPasswordView pwdView;
	private Context mContext;
	private OnQPayPasswordClickListener listener;
	private String myPassword = "";
	private String tips;

	public QPayPasswordDialog(Context mcontext, String tips, OnQPayPasswordClickListener listener) {
		this.mContext = mcontext;
		this.listener = listener;
		this.tips = tips;
	}

	public void show() {
		// 取得自定义View
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		View mydlgView = layoutInflater.inflate(R.layout.qlib_dlg_pay_password, null);
		// mydlgView.setAlpha((float) 0.5);

		myDialog = new AlertDialog.Builder(mContext).create();
		// myDialog.setCancelable(false);
		myDialog.setCanceledOnTouchOutside(false);
		Window wd = myDialog.getWindow();
		wd.setGravity(Gravity.CENTER);
		myDialog.show();

		wd.setContentView(mydlgView);

		wd.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); // 不弹出软键盘解决方案
		pwdView = wd.findViewById(R.id.gpv_normal);
		TextView tv_tip = wd.findViewById(R.id.tv_tip);
		tv_tip.setText(tips);


		WindowManager.LayoutParams params = wd.getAttributes();
		params.width = QUtils.getScreenWitdh(mContext) - 180;
		params.height = 400 ;
		wd.setAttributes(params);

		// 确定
		wd.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (myPassword.equals("")) {
					Toast.makeText(mContext, tips, Toast.LENGTH_LONG).show();
					return;
				}

				if (myPassword.length() < 6) {
					Toast.makeText(mContext, "密码长度必须是六位", Toast.LENGTH_LONG).show();
					return;
				}

				myDialog.dismiss();
				listener.onQPaySubmitClickListener(myPassword);
			}
		});
		// 取消
		wd.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myDialog.dismiss();
				listener.onQPayCancelClickListener();
			}
		});

		pwdView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
			@Override
			public void onTextChanged(String psw) {
				myPassword = psw;
				if (psw.length() == 6) {
					InputMethodManager imm = (InputMethodManager) mContext
							.getSystemService(BaseActivity.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(pwdView.getWindowToken(), 0);
				}
			}

			@Override
			public void onInputFinish(String psw) {
			}
		});

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				ctrHandler.sendEmptyMessage(0);
			}
		}, 300);
	}

	private Handler ctrHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			pwdView.getTextView0().setFocusable(true);
			pwdView.getTextView0().setFocusableInTouchMode(true);
			pwdView.getTextView0().requestFocus();
			InputMethodManager inputManager = (InputMethodManager) pwdView.getTextView0().getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.showSoftInput(pwdView.getTextView0(), 0);
		}
	};

	public interface OnQPayPasswordClickListener {
		void onQPaySubmitClickListener(String password);

		void onQPayCancelClickListener();
	}
}
