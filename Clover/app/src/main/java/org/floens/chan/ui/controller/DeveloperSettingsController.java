package org.floens.chan.ui.controller;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.floens.chan.ChanApplication;
import org.floens.chan.R;
import org.floens.chan.controller.Controller;
import org.floens.chan.core.model.SavedReply;

import java.util.Random;

public class DeveloperSettingsController extends Controller {
    private TextView summaryText;

    public DeveloperSettingsController(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        navigationItem.title = string(R.string.settings_developer);

        LinearLayout wrapper = new LinearLayout(context);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setBackgroundColor(0xffffffff);

        Button crashButton = new Button(context);

        crashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("Debug crash");
            }
        });
        crashButton.setText("Crash the app");

        wrapper.addView(crashButton);

        summaryText = new TextView(context);
        summaryText.setPadding(0, 25, 0, 0);
        wrapper.addView(summaryText);

        setDbSummary();

        Button resetDbButton = new Button(context);
        resetDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChanApplication.getDatabaseManager().reset();
                System.exit(0);
            }
        });
        resetDbButton.setText("Delete database");
        wrapper.addView(resetDbButton);

        Button savedReplyDummyAdd = new Button(context);
        savedReplyDummyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Random r = new Random();
                int j = 0;
                for (int i = 0; i < 100; i++) {
                    j += r.nextInt(10000);
                    ChanApplication.getDatabaseManager().saveReply(new SavedReply("g", j, "pass"));
                }
                setDbSummary();
            }
        });
        savedReplyDummyAdd.setText("Add test rows to savedReply");
        wrapper.addView(savedReplyDummyAdd);

        Button trimSavedReply = new Button(context);
        trimSavedReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ChanApplication.getDatabaseManager().trimSavedRepliesTable(10);
                setDbSummary();
            }
        });
        trimSavedReply.setText("Trim savedreply table");
        wrapper.addView(trimSavedReply);

        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(wrapper);
        view = scrollView;
    }

    private void setDbSummary() {
        String dbSummary = "";
        dbSummary += "Database summary:\n";
        dbSummary += ChanApplication.getDatabaseManager().getSummary();
        summaryText.setText(dbSummary);
    }
}
