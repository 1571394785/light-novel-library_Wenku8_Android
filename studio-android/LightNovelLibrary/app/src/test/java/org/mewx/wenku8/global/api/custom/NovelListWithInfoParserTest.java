package org.mewx.wenku8.global.api.custom;

import org.junit.Test;
import org.mewx.wenku8.global.api.NovelItemInfoUpdate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NovelListWithInfoParserTest {

    @Test
    public void testParseEmptyOrNullString() {
        assertNull(NovelListWithInfoParser.parse(null));
        assertNull(NovelListWithInfoParser.parse(""));
    }

    @Test
    public void testParseInvalidJson() {
        assertNull(NovelListWithInfoParser.parse("invalid json"));
    }

    @Test
    public void testParseValidJson() {
        String json = "{" +
                "  \"page_num\": 1," +
                "  \"items\": [" +
                "    {" +
                "      \"aid\": 123," +
                "      \"Title\": \"Test Title\"," +
                "      \"Author\": \"Test Author\"," +
                "      \"BookStatus\": \"Completed\"," +
                "      \"LastUpdate\": \"2023-10-27\"," +
                "      \"IntroPreview\": \"  Test Intro　\"," +
                "      \"Tags\": \"Tag1, Tag2\"" +
                "    }" +
                "  ]" +
                "}";

        NovelListWithInfoParser.Result result = NovelListWithInfoParser.parse(json);

        assertNotNull(result);
        assertEquals(1, result.pageNum);
        assertEquals(1, result.items.size());

        NovelItemInfoUpdate item = result.items.get(0);
        assertEquals(123, item.aid);
        assertEquals("Test Title", item.title);
        assertEquals("Test Author", item.author);
        assertEquals("Completed", item.status);
        assertEquals("2023-10-27", item.update);
        assertEquals("Test Intro", item.intro_short);
        assertEquals("Tag1, Tag2", item.tags);
    }
}
