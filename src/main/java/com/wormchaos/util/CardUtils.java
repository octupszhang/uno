/**
 * FileName: CardUtils.java
 * Author:   wormchaos
 * Date:     2014-8-6 下午6:53:56
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.wormchaos.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.wormchaos.dto.CardBean;
import com.wormchaos.dto.GameStateBean;
import com.wormchaos.dto.enu.CardColor;
import com.wormchaos.dto.enu.CardName;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author wormchaos
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CardUtils {
    
    private static List<CardBean> cardList;

    /**
     * 
     * 功能描述: <br>
     * 新建卡组，逻辑上只会调用一次
     * 
     * @param gameId
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void initCardList(Long gameId) {
        cardList = new ArrayList<CardBean>();
        int cardId = 0;
        // 1-9,跳过,翻转,+2 每种颜色有两张,但是0和万能牌只有一张
        for (CardColor color : CardColor.values()) {
            for (CardName name : CardName.values()) {
                cardList.add(new CardBean(cardId, name, color));
                cardId++;
                // 如果是0-9，再加一张
                if (!name.equals(CardName.n0) && !name.equals(CardName.wild) && !name.equals(CardName.draw4)) {
                    cardList.add(new CardBean(cardId, name, color));
                    cardId++;
                }
            }
        }
        
    }

    /**
     * 
     * 功能描述: <br>
     * 牌组洗牌
     *
     * @param gameId
     * @param extendCards
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<CardBean> shuffleDeck(Long gameId, List<CardBean> extendCards) {
        if (null == cardList) {
            synchronized (CardUtils.class) {
                if (null == cardList) {
                    initCardList(gameId);
                }
            }
        }
        
        if(!CollectionUtils.isEmpty(extendCards)){
            // bean方法里有equals匹配
            cardList.removeAll(extendCards);            
        }
        // 洗牌
        Collections.shuffle(cardList);
        return cardList;
    }
    
    /**
     * 
     * 功能描述: <br>
     * 抽一张
     *
     * @param gameId
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<CardBean> draw(Long gameId){
        GameStateBean gameState = GameStateUtils.queryGameState(gameId);
        if(null == gameState){
            // TODO 抛出异常
            return null;
        }
        List<CardBean> cards = gameState.getCardList();
        // 如果牌堆空则洗牌
        if(cards.size() == 0){
            // TODO 从所有人手上获取卡信息
            List<CardBean> extendCards = null;
            CardUtils.shuffleDeck(gameId, extendCards);
        }
        CardBean card = cards.get(0);
        cards.remove(0);
        ArrayList<CardBean> list = new ArrayList<CardBean>();
        list.add(card);
        return list;
    }
    
    /**
     * 
     * 功能描述: <br>
     * 抽N张
     *
     * @param gameId
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<CardBean> draw(Long gameId, int times){
        List<CardBean> cards = new ArrayList<CardBean>();
        for (int i = 0; i < times; i++) {
            cards.add(draw(gameId).get(0));
        }
        return cards;
    }
}
