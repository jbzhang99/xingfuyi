
                                <#if order.orderState == 6>
                                <#elseif order.orderState == 1>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>已提交</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>未付款</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>未出库</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>未收货</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <span>未完成</span>
                                    </div>
                                <#elseif order.orderState == 2>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>已提交</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>待确认</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>未出库</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>未收货</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <span>未完成</span>
                                    </div>
                                <#elseif order.orderState == 3>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>已提交</span>
                                    </div>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <#if order.isCodconfim == 1 >
                                        <span>已确认</span>
                                        <#else>
                                        <span>已付款</span>
                                        </#if>
                                    </div>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>备货中</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>未收货</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <span>未完成</span>
                                    </div>
                                <#elseif order.orderState == 4>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>已提交</span>
                                    </div>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <#if order.isCodconfim == 1 >
                                        <span>已确认</span>
                                        <#else>
                                        <span>已付款</span>
                                        </#if>
                                    </div>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>已出库</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>待收货</span>
                                    </div>
                                    <div class="box no-gn">
                                        <i class="spt"></i>
                                        <span>未完成</span>
                                    </div>
                                <#else>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>已提交</span>
                                    </div>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <#if order.isCodconfim == 1 >
                                        <span>已确认</span>
                                        <#else>
                                        <span>已付款</span>
                                        </#if>
                                    </div>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>已出库</span>
                                    </div>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <p></p>
                                        <span>已收货</span>
                                    </div>
                                    <div class="box gn">
                                        <i class="spt"></i>
                                        <span>已完成</span>
                                    </div>
                                </#if>
                            