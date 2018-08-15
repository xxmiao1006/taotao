package com.Alice.search.service.impl;

import com.Alice.common.pojo.SearchItem;
import com.Alice.common.pojo.SearchResult;
import com.Alice.common.pojo.TaotaoResult;
import com.Alice.search.dao.SearchDao;
import com.Alice.search.mapper.SearchItemMapper;
import com.Alice.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alice
 * @date 2018/8/13/013
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private SearchDao searchDao;

    @Override
    public TaotaoResult importAllSearchItems() throws Exception{
        //查询所有商品的数据
        List<SearchItem> searchItemList = searchItemMapper.getSearchItemList();
        //1.创建httpsolrserver 这里我们交由Spring管理
        //2.创建solrinputdocument  将列表中的元素一个个放到索引库中
        for (SearchItem searchItem : searchItemList) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_desc", searchItem.getItem_desc());
            solrServer.add(document);
        }
        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
        //1.创建solrquery对象
        SolrQuery query = new SolrQuery();
        //2.设置组查询条件
        if(StringUtils.isNotBlank(queryString)){
            query.setQuery(queryString);
        }else {
            query.setQuery("*:*");
        }

        if (page == null) {
            page=1;
        }
        if (rows == null) {
            rows=60;
        }
        query.setStart((page-1)*rows);
        query.setRows(rows);

        query.set("df","item_keywords");
        //设置高亮
        query.setHighlight(true);
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        query.addHighlightField("item_title");
        //3.调用dao的方法返回的时SearchResult 只包含总记录数和商品的列表
        SearchResult search = searchDao.search(query);
        //4.设置SearchResult的总页数
        long pageCount = 0;
        pageCount = search.getRecordCount()/rows;
        if(search.getRecordCount()%rows>0){
            pageCount++;
        }
        search.setTotalPages(pageCount);
        //5.返回
        return search;
    }

    @Override
    public TaotaoResult updateItemById(Long itemId) throws Exception {
        return searchDao.updateSearchItemById(itemId);
    }

}
