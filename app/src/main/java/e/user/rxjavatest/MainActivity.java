package e.user.rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import e.user.rxjavatest.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
    }

    //在非UI线程执行，不关注结果
    private void rxJavaTest1(){
        Single.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Log.d("TAG","call 所在线程："+Thread.currentThread().getName());
                return null;
            }
        }).subscribeOn(Schedulers.io()).subscribe();

        Single.defer(new Callable<SingleSource<Integer>>() {
            @Override
            public SingleSource<Integer> call() throws Exception {
                LogUtils.d("Single.defer");
                return Single.just(generateRandom());
            }
        }).subscribeOn(Schedulers.newThread()).subscribe();
    }

    private int generateRandom(){
        LogUtils.d("随机数生成所在的线程：");
        return 25;
    }

    //在非UI线程执行，关注结果
    private void rxJavaTest2(){
        compositeDisposable.add(Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return generateRandom();
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtils.d(integer.toString()+"accept");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.e(throwable.getMessage());
            }
        }));
    }

    //在非UI线程执行，在UI线程关注结果
    private void rxJavaTest3(){
        compositeDisposable.add(Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return generateRandom();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtils.d("随机数"+integer.toString());
            }
        }));
    }
    
    //变换返回值
    private void rxJavaTest4(){
        compositeDisposable.add(Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return generateRandom();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                LogUtils.d("map所在线程为：");
                return integer.toString()+"_map";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.d(s+"返回结果所在线程为：");
            }
        }));
    }

    //按顺序去做某件事，且下一件事依赖上一件事的结果 例如：网络请求接口依赖
    private void rxJavaTest5(){
        compositeDisposable.add(Single.defer(new Callable<SingleSource<Integer>>() {
            @Override
            public SingleSource<Integer> call() throws Exception {
                //第一次网络请求获取用户Id
                LogUtils.d("获取用户ID");
                return Single.just(21);
            }
        }).flatMap(new Function<Integer, SingleSource<String>>() {
            @Override
            public SingleSource<String> apply(Integer integer) throws Exception {
                //第二次网络请求，根据ID获取用户信息
                LogUtils.d("根据ID获取用户信息");
                return Single.just("这个是用户信息");
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.d("最终结果为："+s);
            }
        }));
    }

    //合并不同类型到同一个列表
    private void rxJavaTest6(){
        Single<Book> s1 = Single.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return generateRandom();
            }
        }).map(new Function<Integer, Book>() {
            @Override
            public Book apply(Integer integer) throws Exception {
                return new Book(integer);
            }
        }).subscribeOn(Schedulers.io());
        
        Single<Book> s2 = Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "这是一本书";
            }
        }).map(new Function<String, Book>() {
            @Override
            public Book apply(String s) throws Exception {
                return new Book(s);
            }
        }).subscribeOn(Schedulers.io());
        
        compositeDisposable.add(Single.zip(s1, s2, new BiFunction<Book, Book, List<Book>>() {
            @Override
            public List<Book> apply(Book book, Book book2) throws Exception {
                List<Book> list = new ArrayList<>(2);
                list.add(book);
                list.add(book2);
                return list;
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Consumer<List<Book>>() {
            @Override
            public void accept(List<Book> books) throws Exception {
                for(Book book :books)
                    LogUtils.d(book.toString());
            }
        }));
    }

    //搜索频率限制
    private void rxJavaTest7(){
        compositeDisposable.add(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LogUtils.d("这是搜索内容");
                if(!emitter.isDisposed())emitter.onNext("搜索内容");
            }
        }).debounce(200,TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.d("开始搜索关键字"+s);
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                rxJavaTest1();
                break;
            case R.id.btn2:
                rxJavaTest2();
                break;
            case R.id.btn3:
                rxJavaTest3();
                break;
            case R.id.btn4:
                rxJavaTest4();
                break;
            case R.id.btn5:
                rxJavaTest5();
                break;
            case R.id.btn6:
                rxJavaTest6();
                break;
            case R.id.btn7:
                rxJavaTest7();
                break;
        }
    }

    private class Book{
        int id;
        String name;
        public Book(int id){
            this.id = id;
        }
        
        public Book(String name){
            this.name = name;
        }
        
        @Override
        public String toString(){
            return "Book=>id="+id+",name="+name;
        }
    }
}
