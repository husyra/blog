package com.ronglian.hh.util;

import com.ronglian.hh.domain.CommentUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils<T> {

    public static void pageable(Map<String,Object> map, String pageNo, String pageSize){
        List<Object> resultList = new ArrayList<>();
        List<Object> list = (List<Object>) map.get("RESULT_LIST");

        int totalCount = 0;
        int pageNum = 0;

        if(list!=null){
            totalCount = list.size();
            int pNo = Integer.parseInt(pageNo);
            int pSize = Integer.parseInt(pageSize);

            pageNum = totalCount%pSize==0?totalCount/pSize:totalCount/pSize+1;

            int start =pSize * (pNo-1);
            int end =pSize * pNo;

            if(end>totalCount){
                end = totalCount;
            }

            for(int i=start; i<end; i++){
                resultList.add(list.get(i));
            }

        }

        map.put("PAGE_NO", pageNo);
        map.put("PAGE_SIZE", pageSize);
        map.put("PAGE_NUM", pageNum);
        map.put("RESULT_LIST",resultList);
        map.put("TOTAL_COUNT",totalCount);

        return;
    }

    /**
     * 将评论的评论放入评论中的字段
     * 如：[{id='1', content='', parent='0',owner='', object='', comments=[]},
     *      {id='2', content='', parent='0',owner='', object='', comments=[]},
     *      {id='3', content='', parent='2',owner='', object='', comments=[]}]
     * 转化为
     *      [{id='1', content='', parent='0',owner='', object='', comments=[]},
     *      {id='2', content='', parent='0',owner='', object='', comments={id='3', content='', parent='2',owner='', object='', comments=[]}},
     *      ]
     */
    public static List<CommentUser> commentTrees(List<CommentUser> comments){
        List<CommentUser> resultComments = new ArrayList<>();

        //为了防止重复循环，使用两个集合
        List<CommentUser> parentComments = new ArrayList<>();
        List<CommentUser> childComments = new ArrayList<>();

        for(int i=0; i<comments.size(); i++){
            CommentUser comm = comments.get(i);
            String parent = comm.getParent();
            if("0".equals(parent)){
                //一级评论直接添加
                parentComments.add(comm);
            }else{
                //二级评论添加到对应的父评论的评论字段中
                childComments.add(comm);
            }
        }

        //没有子评论，直接返回
        if(childComments==null || childComments.size()<1){
            //将父级评论放入结果集中
            resultComments.addAll(parentComments);
            return resultComments;
        }

        //循环父评论
        for(int i=0; i<parentComments.size(); i++){

            //每个父评论可能对应多个子评论
            List<CommentUser> childs = new ArrayList<>();

            CommentUser parentComm = parentComments.get(i);
            String commId = parentComm.getId();

            //循环寻找子评论
            if(childComments!=null && childComments.size()>0){
                for(int j=0; j<childComments.size(); j++) {
                    CommentUser childComm = childComments.get(j);
                    String parent = childComm.getParent();

                    if(parent.equals(commId)){
                        //将子评论放入对应的父评论中
                        childs.add(childComm);
                        //一条子评论只有一个父评论，避免重复循环，将已找到一级节点的子节点去除
                        //childComments.remove(childComm); //添加此行代码，数据会少一条
                    }
                }
            }
            parentComm.setChildComm(childs);
            resultComments.add(parentComm);
        }

        return resultComments;
    }
}
