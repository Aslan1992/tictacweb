package kz.myweb.app.mainlogic;

import kz.myweb.app.domain.Item;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BoardTest {

    @Test
    public void shouldReturnNullResponse_ifNoVictoriousOrder_atFirstHorizontalLine() {
        //Given
        Board board = new Board();
        board.init();
        board.setValue(0, 0, Board.CROSS_ITEM);
        board.setValue(0, 1, Board.ZERO_ITEM);
        board.setValue(0, 2, Board.EMPTY_ITEM);
        //WHEN
        Item[] response = board.getVictoryItems();
        //THEN
        assertNull(response);
    }

    @Test
    public void shouldReturnCorrectResponse_ifVictoriousOrderWithCrosses_atFirstHorizontalLine() {
        //Given
        Board board =  new Board();
        board.init();
        board.setValue(0,0, Board.CROSS_ITEM);
        board.setValue(0,1, Board.CROSS_ITEM);
        board.setValue(0,2, Board.CROSS_ITEM);
        //when
        Item[] response = board.getVictoryItems();
        //then
        assertNotNull(response);
        assertThat("(0,0)", equalTo(response[0].toString()));
        assertThat("(0,1)", equalTo(response[1].toString()));
        assertThat("(0,2)", equalTo(response[2].toString()));
    }

    @Test
    public void shouldReturnCorrectResponse_ifVictoriousOrderWithCrosses_atSecondHorizontalLine() {
        //Given
        Board board =  new Board();
        board.init();
        board.setValue(1,0, Board.CROSS_ITEM);
        board.setValue(1,1, Board.CROSS_ITEM);
        board.setValue(1,2, Board.CROSS_ITEM);
        //when
        Item[] response = board.getVictoryItems();
        //then
        assertNotNull(response);
        assertThat("(1,0)", equalTo(response[0].toString()));
        assertThat("(1,1)", equalTo(response[1].toString()));
        assertThat("(1,2)", equalTo(response[2].toString()));
    }

    @Test
    public void shouldReturnCorrectResponse_ifVictoriousOrderWithZeros_atThirdHorizontalLine() {
        //Given
        Board board =  new Board();
        board.init();
        board.setValue(2,0, Board.ZERO_ITEM);
        board.setValue(2,1, Board.ZERO_ITEM);
        board.setValue(2,2, Board.ZERO_ITEM);
        //when
        Item[] response = board.getVictoryItems();
        //then
        assertNotNull(response);
        assertThat("(2,0)", equalTo(response[0].toString()));
        assertThat("(2,1)", equalTo(response[1].toString()));
        assertThat("(2,2)", equalTo(response[2].toString()));
    }

    @Test
    public void shouldReturnNullResponse_ifAllItemsEmpty_atFirstVerticalLine() {
        //Given
        Board board = new Board();
        board.init();
        board.setValue(0, 0, Board.EMPTY_ITEM);
        board.setValue(1, 0, Board.EMPTY_ITEM);
        board.setValue(2, 0, Board.EMPTY_ITEM);
        //WHEN
        Item[] response = board.getVictoryItems();
        //THEN
        assertNull(response);
    }

    @Test
    public void shouldReturnCorrectResult_ifVictoriousOrderWithCrosses_atFirstVerticalLine() {
        //given
        Board board = new Board();
        board.init();
        board.setValue(0, 0, Board.CROSS_ITEM);
        board.setValue(1, 0, Board.CROSS_ITEM);
        board.setValue(2, 0, Board.CROSS_ITEM);
        //when
        Item[] result = board.getVictoryItems();
        //then
        assertNotNull(result);
        assertThat("(0,0)", equalTo(result[0].toString()));
        assertThat("(1,0)", equalTo(result[1].toString()));
        assertThat("(2,0)", equalTo(result[2].toString()));
    }

    @Test
    public void shouldReturnCorrectResult_ifVictoriousOrderWithCrosses_atSecondVerticalLine() {
        //given
        Board board = new Board();
        board.init();
        board.setValue(0, 1, Board.CROSS_ITEM);
        board.setValue(1, 1, Board.CROSS_ITEM);
        board.setValue(2, 1, Board.CROSS_ITEM);
        //when
        Item[] result = board.getVictoryItems();
        //then
        assertNotNull(result);
        assertThat("(0,1)", equalTo(result[0].toString()));
        assertThat("(1,1)", equalTo(result[1].toString()));
        assertThat("(2,1)", equalTo(result[2].toString()));
    }

    @Test
    public void shouldReturnCorrectResult_ifVictoriousOrderWithCrosses_atThirdVerticalLine() {
        //given
        Board board = new Board();
        board.init();
        board.setValue(0, 2, Board.CROSS_ITEM);
        board.setValue(1, 2, Board.CROSS_ITEM);
        board.setValue(2, 2, Board.CROSS_ITEM);
        //when
        Item[] result = board.getVictoryItems();
        //then
        assertNotNull(result);
        assertThat("(0,2)", equalTo(result[0].toString()));
        assertThat("(1,2)", equalTo(result[1].toString()));
        assertThat("(2,2)", equalTo(result[2].toString()));
    }

    @Test
    public void shouldReturnCorrectResult_idVictoriesWithCrosses_atLeftToRightDiagonal() {
        //given
        Board board = new Board();
        board.init();
        board.setValue(0,0, Board.CROSS_ITEM);
        board.setValue(1,1, Board.CROSS_ITEM);
        board.setValue(2,2, Board.CROSS_ITEM);
        //when
        Item[] result = board.getVictoryItems();
        //then
        assertNotNull(result);
        assertThat("(0,0)", equalTo(result[0].toString()));
        assertThat("(1,1)", equalTo(result[1].toString()));
        assertThat("(2,2)", equalTo(result[2].toString()));
    }

    @Test
    public void shouldReturnCorrectResult_ifVictoriesWithCrosses_atRightToLeftDiagonal() {
        //given
        Board board = new Board();
        board.init();
        board.setValue(0,2, Board.CROSS_ITEM);
        board.setValue(1,1, Board.CROSS_ITEM);
        board.setValue(2,0, Board.CROSS_ITEM);
        //when
        Item[] result = board.getVictoryItems();
        //then
        assertNotNull(result);
        assertThat("(0,2)", equalTo(result[0].toString()));
        assertThat("(1,1)", equalTo(result[1].toString()));
        assertThat("(2,0)", equalTo(result[2].toString()));
    }
}
