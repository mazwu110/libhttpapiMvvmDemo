# libhttpapiMvvmDemo
自己封装的基于谷歌AAC  MVVM框架:
1 简单介绍AAC 的 MVVM 框架使用
2 给大家推荐自己封装的 libhttpapi 泛型网络通信框架，是基于 retrofit2和rxjava2封装的，很方便使用，网上貌似还没找到类似的封装，
相当于MVVM中的model，所有的VM用一个model就搞定，只需要传一个解析的对象（class）进去，就可以解析后台返回的所有JSON格式数据，
使用方式demo中有，不懂的可以qq哦。可以说是泛型MVVM了，喜欢的拿去用吧，真诚奉献，本来想找一个基于retrofit 和rxjava泛型解析的，但是真的
没找到，只能自己封装了，另外看了很多网上的 都是用很多个 ApiService,但是这里本人
只用了一个就实现了，还是泛型的，里面就只有get，post方法，传请求地址和参数和class进去就能出结果哦
