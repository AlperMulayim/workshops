package com.alper.streamcars;

import com.alper.streamcars.employees.EmpDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
class StreamCarsApplicationTests {

	@Test
	void contextLoads() {
		EmpDto dto  =  new EmpDto("Wat","Cyprus",1344);
		EmpDto emptyDto = new EmpDto();
		EmpDto dt = null;
		Optional<EmpDto> dtoOpt = Optional.of(dto);
		Optional<EmpDto> empOpt = Optional.empty();
		Optional<EmpDto> empNull = Optional.ofNullable(dt);

		System.out.println (empOpt +" -> " + empOpt.isEmpty());
		System.out.println(dtoOpt);
		System.out.println(dtoOpt + " ->  " + dtoOpt.isPresent() );

		assertThrows(NoSuchElementException.class,()-> empOpt.orElseThrow());
		assertThrows(NoSuchElementException.class,()-> empNull.orElseThrow());
		assertTrue(empOpt.isEmpty());
		assertTrue(dtoOpt.isPresent());

		assertEquals(emptyDto,empOpt.orElseGet(()->emptyDto));

		int x = 10;
		int y = 30;
		int result = Optional.of(y/x).orElse(5);
		assertNotEquals(5,result);
		assertEquals(3,result);

		assertThrows(NullPointerException.class, ()-> empOpt.orElseThrow(()-> new NullPointerException()));

		assertNotEquals(emptyDto,dtoOpt.orElse(emptyDto));
		assertEquals(dto,empOpt.orElse(dto));
		assertEquals(dto,dtoOpt.orElse(emptyDto));

		dtoOpt.ifPresent((val)-> System.out.println("ifpresent data: " + val));
	}

	@Test
	void testConverts(){
		List<Integer> list = List.of(1,2,4,5,6,7,8);
		int[] arr = {10,11,12,13,14,15};
		List<Integer> expected = List.of(10,11,12,13,14,15);
		int[] arrExpected = {1,2,4,5,6,7,8};

		List<Integer> convList = IntStream.of(arr).boxed().toList();

		assertEquals(convList,expected);

		List<Integer> conv2 = Arrays.stream(arr)
				.map(Integer::new)
				.boxed()
				.collect(Collectors.toList());

		assertEquals(conv2,expected);

		int[] convArr = list.stream()
				.mapToInt(Integer::intValue)
				.toArray();

		assertArrayEquals(convArr,arrExpected);

	}

}
