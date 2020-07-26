package cn.bo.project;

import cn.bo.project.es.model.User;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;


@SpringBootTest
class ProjectEsApplicationTests {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Test
    void contextLoads() {
    }

    @Test
    void createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("zhangbo");
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.index());
    }

    @Test
    void existIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("zhangbo");
        Boolean exist =  client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exist);
    }

    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("zhangbo");
        AcknowledgedResponse acknowledgedResponse =  client.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());
    }

    @Test
    void addDocument() throws IOException {
        User user = new User(1l, "zhangbo", 16);
        IndexRequest request = new IndexRequest("zhangbo");
        request.id(user.getId().toString());

        request.source(JSON.toJSONString(user), XContentType.JSON);

        IndexResponse indexResponse = client.index(request,RequestOptions.DEFAULT);

        System.out.println(indexResponse.status());
        System.out.println(indexResponse.toString());
    }

    @Test
    void getDocument() throws IOException {
        GetRequest getRequest = new GetRequest("zhangbo","1");
        GetResponse getResponse = client.get(getRequest,RequestOptions.DEFAULT);
        System.out.println(getResponse.toString());
        System.out.println(getResponse.getSourceAsString());
    }

    @Test
    void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("zhangbo","1");
        User user = new User(1l, "zhangbo111", 16);
        updateRequest.doc(JSON.toJSONString(user),XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }

    @Test
    void deleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("zhangbo","1");
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse);
    }

    @Test
    void bulkAddDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        ArrayList<User> userArrayList = new ArrayList<>();
        userArrayList.add(new User(2l,"zhangbo2",2));
        userArrayList.add(new User(3l,"zhangbo3",3));
        userArrayList.add(new User(4l,"zhangbo4",4));
        userArrayList.add(new User(5l,"zhangbo5",5));
        userArrayList.add(new User(6l,"zhangbo6",6));
        userArrayList.forEach(user -> {
            bulkRequest.add(
                    new IndexRequest("zhangbo")
                            .id(user.getId().toString())
                            .source(JSON.toJSONString(user),XContentType.JSON)
            );
        });
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());
    }

    @Test
    void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest("zhangbo");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 精准查询
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("age",3);
        sourceBuilder.query(queryBuilder);

        // matchAll
        /*MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(matchAllQueryBuilder);*/

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("-----------");
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
    }
}
