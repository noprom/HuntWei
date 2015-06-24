package com.huntdreams.wei.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表Model
 *
 * @author noprom (https://github.com/noprom)
 * @version 1.0
 * Created by noprom on 2015/6/7.
 */
public class MessageListModel implements BaseListModel<MessageModel, MessageListModel>{

    private List<MessageModel> statuses = new ArrayList<MessageModel>();

    @Override
    public int getSize() {
        return statuses.size();
    }

    @Override
    public MessageModel get(int position) {
        return statuses.get(position);
    }

    @Override
    public List<MessageModel> getList() {
        return statuses;
    }

    @Override
    public void addAll(boolean toTop, MessageListModel values) {
        if (values != null && values.getSize() > 0) {
            for (MessageModel msg : values.getList()) {
                if (!statuses.contains(msg)) {
                    statuses.add(toTop ? 0 : statuses.size(), msg);
                }
            }
            total_number = values.total_number;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total_number);
        dest.writeString(previous_cursor);
        dest.writeString(next_cursor);
        dest.writeTypedList(statuses);
    }

    public static final Parcelable.Creator<MessageListModel> CREATOR = new Parcelable.Creator<MessageListModel>() {
        @Override
        public MessageListModel createFromParcel(Parcel in) {
            MessageListModel ret = new MessageListModel();
            ret.total_number = in.readInt();
            ret.previous_cursor = in.readString();
            ret.next_cursor = in.readString();
            in.readTypedList(ret.statuses, MessageModel.CREATOR);
            return ret;
        }

        @Override
        public MessageListModel[] newArray(int size) {
            return new MessageListModel[size];
        }
    };
}
