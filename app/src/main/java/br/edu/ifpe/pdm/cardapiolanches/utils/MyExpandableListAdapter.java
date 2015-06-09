package br.edu.ifpe.pdm.cardapiolanches.utils;

/**
 * Created by Richardson on 19/05/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import br.edu.ifpe.pdm.cardapiolanches.R;
import br.edu.ifpe.pdm.cardapiolanches.bean.Produto;
import br.edu.ifpe.pdm.cardapiolanches.view.cliente.PedidosListActivity;

public class MyExpandableListAdapter extends BaseExpandableListAdapter{

    private final SparseArray<GroupProduto> groups;
    public LayoutInflater inflater;
    private Activity activity;
    private final Context context;

    public MyExpandableListAdapter(Activity act, SparseArray<GroupProduto> groups, Context ctx) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
        context = ctx;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }


    private int groupPositionTemp;
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Produto children = (Produto) getChild(groupPosition, childPosition);
        groupPositionTemp = groupPosition;
        TextView text = null;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.listrow_details, null);

        }
        text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(children.toString());
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produto children = (Produto) getChild(groupPositionTemp, childPosition);
                Map<Integer, Produto> map;

                map= new HashMap<Integer, Produto>();
                map.put(1, children);

                Intent intent = new Intent(context, PedidosListActivity.class);
//                           intent.putExtra("produto", (java.io.Serializable) map);

                context.startActivity(intent);

//                    OfertasListExpadableActivity.forwardNextActivity(v);
            }
        });


        return convertView;
    }




    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_group, null);
        }
        GroupProduto groupProduto = (GroupProduto) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(groupProduto.string);
        ((CheckedTextView) convertView).setChecked(isExpanded);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}