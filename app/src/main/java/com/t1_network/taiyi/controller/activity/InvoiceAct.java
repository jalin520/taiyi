package com.t1_network.taiyi.controller.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.t1_network.core.controller.BasicAct;
import com.t1_network.core.utils.KeyBoardUtils;
import com.t1_network.core.utils.ResUtils;
import com.t1_network.taiyi.R;
import com.t1_network.taiyi.model.bean.order.Receipt;
import com.t1_network.taiyi.model.verify.InvoiceEmailVerify;
import com.t1_network.taiyi.model.verify.InvoiceTitleVerify;
import com.t1_network.taiyi.model.verify.VerifyResult;

import butterknife.Bind;
import butterknife.OnClick;

public class InvoiceAct extends BasicAct implements View.OnClickListener {


    @Bind(R.id.act_invoice_ll_title_invoice)
    LinearLayout llTitleSelect;

    @Bind(R.id.act_invoice_ll_no_invoice)
    LinearLayout llNoInvoice;//不需要发票btn

    @Bind(R.id.act_invoice_ll_tv_no_invoice)
    TextView tvNoInvoice;//不需要发票text

    @Bind(R.id.act_invoice_ll_email_invoice)
    LinearLayout llEmailInvoice;//点知发票btn

    @Bind(R.id.act_invoice_ll_tv_email_invoice)
    TextView tvEmailInvoice;//点知发票text

    @Bind(R.id.act_invoice_ll_invoice)
    LinearLayout llInvoice; //纸质发票btn

    @Bind(R.id.act_invoice_ll_tv_invoice)
    TextView tvInvoice;//纸质发票text

    @Bind(R.id.act_invoice_ll_personal_invoice)
    LinearLayout llPreInvoice; //个人发票btn

    @Bind(R.id.act_invoice_iv_personal_invoice)
    ImageView ivPreInvoice;//个人发票image

    @Bind(R.id.act_invoice_tv_personal_invoice)
    TextView tvPreInvoice; //个人发票tv;

    @OnClick(R.id.act_invoice_ll_personal_invoice)
    public void selectPersonal() {
        isPersonal = true;
        btnSelector(ivPreInvoice, tvPreInvoice, true);
        btnSelector(ivComInvoice, tvComInvoice, false);
    }



    @Bind(R.id.act_invoice_ll_company_invoice)
    LinearLayout llComInvoice; //单位发票btn

    @Bind(R.id.act_invoice_iv_company_invoice)
    ImageView ivComInvoice;//单位发票image


    @OnClick(R.id.act_invoice_ll_company_invoice)
    public void selectNoPersonal() {
        isPersonal = false;
        btnSelector(ivPreInvoice, tvPreInvoice, false);
        btnSelector(ivComInvoice, tvComInvoice, true);
    }

    @Bind(R.id.act_invoice_tv_company_invoice)
    TextView tvComInvoice; //单位发票tv;


    @Bind(R.id.act_invoice_ll_et_email)
    LinearLayout llEtEmail; //电子输入框

    @Bind(R.id.act_invoice_ll_input_et)
    LinearLayout llInput; //输入框Root

    @Bind(R.id.act_invoice_tv_title_confirm)
    TextView tvTitleConfirm;

    @Bind(R.id.act_invoice_et_email)
    EditText editEmail;

    @Bind(R.id.act_invoice_et_title)
    EditText editTitle;

    @Bind(R.id.act_invoice_et_other)
    EditText editContent;


    private LinearLayout mLlRoot;


    private static final int TYPE_NO_NEED = 1;
    private static final int TYPE_ELEC_INVOICE = 2;
    private static final int TYPE_PAPER_INVOICE = 3;

    private int type = TYPE_NO_NEED;

    private boolean isPersonal = true;

    public InvoiceAct() {
        super(R.layout.act_invoice, R.string.act_title_activity_invoice);
    }

    public static final String P_RECEIPT = "P_RECEIPT";

    public static final int RC_SELECT_INVOICE = 2;

    public static void startActivity(Activity context) {
        Intent intent = new Intent(context, InvoiceAct.class);
        context.startActivityForResult(intent, RC_SELECT_INVOICE);
    }

    @Override
    public void initView() {
        mLlRoot = (LinearLayout) findViewById(R.id.act_invoice_ll_root);
        mLlRoot.setOnClickListener(this);

        llNoInvoice.setOnClickListener(this);
        llInvoice.setOnClickListener(this);
        llEmailInvoice.setOnClickListener(this);


        btnSelector(llNoInvoice, tvNoInvoice, true);
        btnSelector(llEmailInvoice, tvEmailInvoice, false);
        btnSelector(llInvoice, tvInvoice, false);
        llInput.setVisibility(View.GONE);


        selectPersonal();
    }


    @OnClick(R.id.act_invoice_tv_title_confirm)
    public void submit() {
        Receipt receipt = new Receipt();

        //填充对应信息

        switch (type) {

            case TYPE_NO_NEED:
                receipt = null;
                break;

            case TYPE_ELEC_INVOICE: {
                receipt.setEmail(editEmail.getText().toString());
                receipt.setTitle(editTitle.getText().toString());
                receipt.setContent(editContent.getText().toString());
                receipt.setType("电子发票");
                receipt.setIsPersonal(isPersonal);
                VerifyResult invoiceEmailResult = InvoiceEmailVerify.verify(receipt.getEmail());
                if (!invoiceEmailResult.getResult()) {
                    showTip(invoiceEmailResult.getErrorMsg());
                    return;
                }
                VerifyResult invoiceTitleResult = InvoiceTitleVerify.verify(receipt.getTitle());
                if (!invoiceTitleResult.getResult()) {
                    showTip(invoiceTitleResult.getErrorMsg());
                    return;
                }
            }
                break;

            case TYPE_PAPER_INVOICE: {
                receipt.setTitle(editTitle.getText().toString());
                receipt.setContent(editContent.getText().toString());
                receipt.setType("纸质发票");
                receipt.setIsPersonal(isPersonal);
                VerifyResult invoiceTitleResult = InvoiceTitleVerify.verify(receipt.getTitle());
                if (!invoiceTitleResult.getResult()) {
                    showTip(invoiceTitleResult.getErrorMsg());
                    return;
                }

            }
                break;
        }




        //end

        Intent intent = new Intent();
        intent.putExtra(P_RECEIPT, receipt);

        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.act_invoice_ll_no_invoice:
                KeyBoardUtils.hideKeyboard(this, mLlRoot);
                type = TYPE_NO_NEED;


                btnSelector(llNoInvoice, tvNoInvoice, true);
                btnSelector(llEmailInvoice, tvEmailInvoice, false);
                btnSelector(llInvoice, tvInvoice, false);


                if (llInput.getVisibility() == View.VISIBLE)
                    llInput.setVisibility(View.GONE);

                break;
            case R.id.act_invoice_ll_invoice:
                KeyBoardUtils.hideKeyboard(this, mLlRoot);
                type = TYPE_PAPER_INVOICE;


                btnSelector(llNoInvoice, tvNoInvoice, false);
                btnSelector(llEmailInvoice, tvEmailInvoice, false);
                btnSelector(llInvoice, tvInvoice, true);
                if (llInput.getVisibility() == View.GONE) {
                    llInput.setVisibility(View.VISIBLE);
                }
                if (llEtEmail.getVisibility() == View.VISIBLE)
                    llEtEmail.setVisibility(View.GONE);

                break;
            case R.id.act_invoice_ll_email_invoice:
                KeyBoardUtils.hideKeyboard(this, mLlRoot);
                type = TYPE_ELEC_INVOICE;

                btnSelector(llNoInvoice, tvNoInvoice, false);
                btnSelector(llEmailInvoice, tvEmailInvoice, true);
                btnSelector(llInvoice, tvInvoice, false);
                if (llInput.getVisibility() == View.GONE) {
                    llInput.setVisibility(View.VISIBLE);

                }
                if (llEtEmail.getVisibility() == View.GONE)
                    llEtEmail.setVisibility(View.VISIBLE);
                break;

            case R.id.act_invoice_ll_root:
                KeyBoardUtils.hideKeyboard(this, mLlRoot);
                break;

        }

    }

    private void btnSelector(ViewGroup viewGroup, TextView textView, boolean isSelectored) {
        if (isSelectored) {
            viewGroup.setBackgroundResource(R.drawable.btn_in_login_full_info);
            textView.setTextColor(ResUtils.getColor(R.color.text_white));
        } else {
            viewGroup.setBackgroundResource(R.drawable.btn_in_invoice_full_info);
            textView.setTextColor(ResUtils.getColor(R.color.text_gray_deep));
        }

    }

    private void btnSelector(ImageView iv, TextView textView, boolean isSelectored) {
        if (isSelectored) {

            iv.setImageResource(R.drawable.ic_checked);
            textView.setTextColor(ResUtils.getColor(R.color.text_black));

        } else {
            iv.setImageResource(R.drawable.ic_check);
            textView.setTextColor(ResUtils.getColor(R.color.text_gray_deep));
        }

    }


    private boolean isOpened = true;

    private void toggle(boolean animated, ViewGroup view) {
        view.measure(0, 0);

        int height = view.getMeasuredHeight();
//        int mtitleHeight = mRlTitle.getMeasuredHeight();

        if (isOpened) {
            if (animated) {
                int start = height;
                int end = 0;
                doAnimation(start, end, view);
            } else {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = 0;
                view.setLayoutParams(params);
            }


        } else {
            if (animated) {
                int start = 0;
                int end = height;
                doAnimation(start, end, view);
            } else {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = height;
                view.setLayoutParams(params);
            }

        }

        isOpened = !isOpened;
    }

    private void doAnimation(int start, int end, final ViewGroup view) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animator.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = animatedValue;
                view.setLayoutParams(params);
            }
        });
        animator.start();
    }

}
