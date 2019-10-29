package com.yixiekeji.web.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.service.cart.ICartService;
import com.yixiekeji.service.member.IMemberBalancePayLogService;
import com.yixiekeji.service.member.IMemberGradeDownLogsService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.sale.ISaleOrderService;
import com.yixiekeji.service.search.ISolrProductService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.service.settlement.ISettlementService;

/***
 * 系统定时器
 *                       
 * @Filename: JobConfig.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wangpeng@yixiekeji.com
 *
 */
@Component
public class JobConfig {

    private static Logger               log = LoggerFactory.getLogger(JobConfig.class);

    @Resource
    private ISettlementService          settlementService;
    @Resource
    private IOrdersService              ordersService;
    @Resource
    private ICartService                cartService;
    @Resource
    private ISellerService              sellerService;
    @Resource
    private ISolrProductService         solrProductService;
    @Resource
    private IMemberGradeDownLogsService memberGradeDownLogsService;
    @Resource
    private IMemberBalancePayLogService memberBalancePayLogService;
    @Resource
    private ISaleOrderService           saleOrderService;
    @Resource
    private DomainUrlUtil               domainUrlUtil;

    /**
     * 商家结算账单生成定时任务<br>
     * <li>查询所有商家，每个商家每个结算周期生成一条结算账单
     * <li>计算周期内商家所有的订单金额合计
     * <li>计算所有订单下网单的佣金
     * <li>计算周期内发生的退货退款（当前周期结算的订单如果在下个结算周期才退款，退款结算在下个周期计算）
     * <li>每个商家一个事务，某个商家结算时发生错误不影响其他结算
     */
    @Scheduled(cron = "0 15 0 16 * ?")
    public void jobSettlement() {
        log.info("jobSettlement() start");
        ServiceResult<Boolean> jobResult = settlementService.jobSettlement();
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error(
                "[yixiekeji-admin][AdminJob][jobSettlement] 商家结算账单生成时失败：" + jobResult.getMessage());
        }
        log.info("jobSettlement() end");
    }

    /**
     * 系统自动完成订单<br>
     * <li>对已发货状态的订单发货时间超过15个自然日的订单进行自动完成处理
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void jobSystemFinishOrder() {
        log.info("jobSystemFinishOrder() start");
        ServiceResult<Boolean> jobResult = ordersService.jobSystemFinishOrder();
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-admin][AdminJob][jobSystemFinishOrder] 系统自动完成订单时失败："
                      + jobResult.getMessage());
        }
        log.info("jobSystemFinishOrder() end");
    }

    /**
     * 系统定时任务清除7天之前添加的购物车数据
     */
    @Scheduled(cron = "0 10 2 * * ?")
    public void jobClearCart() {
        log.info("jobClearCart() start");
        ServiceResult<Boolean> jobResult = cartService.jobClearCart();
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-admin][AdminJob][jobClearCart] 系统定时任务清除7天之前添加的购物车数据时失败："
                      + jobResult.getMessage());
        }
        log.info("jobClearCart() end");
    }

    /**
     * 系统定时更新solr索引
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void jobSearchSolr() {
        log.info("jobSearchSolr() start");
        String solrUrl = domainUrlUtil.getSearchSolrUrl();
        String solrServer = domainUrlUtil.getSearchSolrServer();
        ServiceResult<Boolean> jobResult = solrProductService.jobCreateIndexesSolr(solrUrl,
            solrServer);
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-admin][AdminJob][jobSearchSolr] 系统定时任务定时生成Solr索引失败："
                      + jobResult.getMessage());
        }
        log.info("jobSearchSolr() end");
    }

    /**
     * 更新敏感词的索引值
     */
    @Scheduled(cron = "0 0 5 * * ?")
    public void jobUpdateSearchRecordIndex() {
        log.info("jobUpdateSearchRecordIndex() start");
        String solrUrl = domainUrlUtil.getSearchSolrUrl();
        String solrServer = domainUrlUtil.getSearchSolrServer();
        ServiceResult<Boolean> jobResult = solrProductService.jobUpdateSearchRecordIndex(solrUrl,
            solrServer);
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-admin][AdminJob][jobUpdateSearchRecordIndex] 系统定时任务定时更新敏感词的索引值："
                      + jobResult.getMessage());
        }
        log.info("jobUpdateSearchRecordIndex() end");
    }

    /**
     * 定时任务设定商家的评分，用户评论各项求平均值设置为商家各项的综合评分
     * @return
     */
    @Scheduled(cron = "0 15 0 * * ?")
    public void jobSetSellerScore() {
        log.info("jobSetSellerScore() start");
        ServiceResult<Boolean> jobResult = sellerService.jobSetSellerScore();
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-admin][AdminJob][jobSetSellerScore] 定时任务设定商家的评分时失败："
                      + jobResult.getMessage());
        }
        log.info("jobSetSellerScore() end");
    }

    /**
     * 系统自动取消24小时没有付款订单
     * @return
     */
    @Scheduled(cron = "0 5/10 * * * ?")
    public void jobSystemCancelOrder() {
        log.info("jobSystemCancelOrder() start");
        ServiceResult<Boolean> jobResult = ordersService.jobSystemCancelOrder();
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-admin][AdminJob][jobSystemCancelOrder] 定时任务系统自动取消24小时没有付款订单时失败："
                      + jobResult.getMessage());
        }
        log.info("jobSystemCancelOrder() end");
    }

    /**
     * 定时任务设定商家各项统计数据
     * @return
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void jobSellerStatistics() {
        log.info("jobSellerStatistics() start");
        ServiceResult<Boolean> jobResult = sellerService.jobSellerStatistics();
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-admin][AdminJob][jobSellerStatistics] 定时任务设定商家各项统计数据时失败："
                      + jobResult.getMessage());
        }
        log.info("jobSellerStatistics() end");
    }

   

    /**
     * 每天执行一次，对每年当天注册的会员减去相应的经验值数量，影响会员等级（处理完成后检查前2天的执行情况防止服务器维护等原因导致的定时任务未执行）
     */
    @Scheduled(cron = "0 20 4 * * ?")
    public void jobGradeValueDown() {
        log.info("jobGradeValueDown() start");
        ServiceResult<Boolean> jobResult = memberGradeDownLogsService.jobGradeValueDown();
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-admin][AdminJob][jobGradeValueDown] 执行会员年度经验值递减任务时失败："
                      + jobResult.getMessage());
        }
        log.info("jobGradeValueDown() end");
    }

    /**
     * 每天执行一次，定时器定时根据已经完成的订单，生成对应的佣金
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void jobSaveSaleOrder() {
        log.info("jobSaveSaleOrder() start");
        ServiceResult<Boolean> jobResult = saleOrderService.jobSaveSaleOrder();
        if (!jobResult.getSuccess() || jobResult.getResult() == null || !jobResult.getResult()) {
            log.error("[yixiekeji-admin][AdminJob][jobSaveSaleOrder] 定时器定时根据已经完成的订单，生成对应的佣金失败："
                      + jobResult.getMessage());
        }
        log.info("jobSaveSaleOrder() end");
    }

}
