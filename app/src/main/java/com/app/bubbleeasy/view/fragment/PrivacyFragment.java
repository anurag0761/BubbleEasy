package com.app.bubbleeasy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.app.bubbleeasy.R;
import com.app.bubbleeasy.databinding.FragmentWebviewBinding;


public class PrivacyFragment extends Fragment {

    FragmentWebviewBinding binding;

    public PrivacyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_webview, container, false);

        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl("http://bubbleeasy.com/privacy_policy.html");

        return binding.getRoot();
    }


}
