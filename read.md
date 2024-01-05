# u8 补丁
## 介绍
这是一个用于聚合u8补丁的项目。用爬虫去爬取各种u8补丁msi类补丁和sql类补丁，聚合后方便搜索。  
顺便练习多线程网络爬虫。

## 入口

- U8PatchApplication 启动应用
- CrawlCommand 爬取列表
- CrawlDetailCommand 爬取详情

## todo
-[ ] 日志打印无法正常采用slf4j输出，需要解决。
-[ ] sql patch 旧项目有做，新的不想做的，感觉没啥意思