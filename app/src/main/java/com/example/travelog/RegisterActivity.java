package com.example.travelog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private TextView tvTest;
    private EditText editText1;
    private EditText editText2;
    private Button  Btn_signin;
    private Button Btn_signup;

    private String userName,psw,spPsw;//get username and password
    private EditText et_user_name,et_psw;//edit
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvTest=findViewById(R.id.logo);
        tvTest.animate().translationYBy(60).setDuration(2000).start();

        editText1=findViewById(R.id.et_1);
        editText1.animate().alpha(0).setDuration(0).start();
        editText1.animate().alpha(1).setDuration(2000).start();

        editText2=findViewById(R.id.et_2);
        editText2.animate().alpha(0).setDuration(0).start();
        editText2.animate().alpha(1).setDuration(2000).start();
        //login
        Btn_signin=findViewById(R.id.btn_signin);
        Btn_signin.animate().rotationYBy(360).setDuration(1000).start();
        //register
        Btn_signup=findViewById(R.id.btn_signup);
        Btn_signup.animate().translationYBy(-60).setDuration(2000).start();
        Btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到 注册界面
                Intent intent=new Intent(RegisterActivity.this, signup_page_Activity.class);
                startActivity(intent);
            }
        });



        pref= PreferenceManager.getDefaultSharedPreferences(this);
        login();
    }

    private void login() {
        //从signup_page的activity里那数据
        et_user_name= (EditText) findViewById(R.id.et_1);
        et_psw= (EditText) findViewById(R.id.et_2);

        //Login button click event
        Btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start logging in to get username and password  getText().toString().trim();
                userName=et_user_name.getText().toString().trim();
                psw=et_psw.getText().toString().trim();
                // Define the method readPsw to get the password in order to read the username
                spPsw=readPsw(userName);
                // TextUtils.isEmpty
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "Please enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(psw.equals(spPsw)){
                    //一致登录成功
                    Toast.makeText(RegisterActivity.this, "Welcome "+editText1.getText().toString(), Toast.LENGTH_SHORT).show();
                    editor=pref.edit();
                    editor.apply();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    User.setName(userName);
                    saveLoginStatus(true, userName);
                    //登录成功后关闭此页面进入主页
                    Intent data=new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin",true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK,data);
                    //销毁登录界面
                    RegisterActivity.this.finish();
                    //跳转到主界面，登录成功的状态传递到 MainActivity 中
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    return;
                }else if((spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!psw.equals(spPsw))){
                    Toast.makeText(RegisterActivity.this, "The Username and Password entered are inconsistent", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegisterActivity.this, "This Username does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userName){
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName , "");
    }
    /**
     *保存登录状态和登录用户名到SharedPreferences中
     * 本地日志
     */
    private void saveLoginStatus(boolean status,String userName){
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
    }

    private boolean readLoginStatus(){
        boolean log_status;
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        log_status=sp.getBoolean("isLogin" , false);
        return log_status;
    }
    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                et_user_name.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(userName.length());
            }
        }
    }
}
