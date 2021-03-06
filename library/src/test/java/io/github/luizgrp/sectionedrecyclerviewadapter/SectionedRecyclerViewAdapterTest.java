package io.github.luizgrp.sectionedrecyclerviewadapter;

import org.junit.Test;

import io.github.luizgrp.sectionedrecyclerviewadapter.testdoubles.spy.BindingFootedStatelessSectionSpy;
import io.github.luizgrp.sectionedrecyclerviewadapter.testdoubles.spy.BindingHeadedFootedStatelessSectionSpy;
import io.github.luizgrp.sectionedrecyclerviewadapter.testdoubles.spy.BindingHeadedStatelessSectionSpy;
import io.github.luizgrp.sectionedrecyclerviewadapter.testdoubles.spy.BindingStatelessSectionSpy;
import io.github.luizgrp.sectionedrecyclerviewadapter.testdoubles.stub.FootedStatelessSectionStub;
import io.github.luizgrp.sectionedrecyclerviewadapter.testdoubles.stub.HeadedFootedStatelessSectionStub;
import io.github.luizgrp.sectionedrecyclerviewadapter.testdoubles.stub.HeadedStatelessSectionStub;
import io.github.luizgrp.sectionedrecyclerviewadapter.testdoubles.stub.StatelessSectionStub;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SectionedRecyclerViewAdapterTest {

    final int ITEMS_QTY = 10;
    final String SECTION_TAG = "tag";

    @Test
    public void getItemCount_emptyAdapter_isZero() {
        assertThat(new SectionedRecyclerViewAdapter().getItemCount(), is(0));
    }

    @Test
    public void getSectionsMap_emptyAdapter_isEmpty() {
        assertTrue(new SectionedRecyclerViewAdapter().getSectionsMap().isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getSectionPosition_emptyAdapter_throwsException() {
        new SectionedRecyclerViewAdapter().getSectionPosition(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getSectionForPosition_emptyAdapter_throwsException() {
        new SectionedRecyclerViewAdapter().getSectionForPosition(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getItemViewType_emptyAdapter_throwsException() {
        new SectionedRecyclerViewAdapter().getItemViewType(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getSectionItemViewType_emptyAdapter_throwsException() {
        new SectionedRecyclerViewAdapter().getSectionItemViewType(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void onBindViewHolder_emptyAdapter_throwsException() {
        new SectionedRecyclerViewAdapter().onBindViewHolder(null, 0);
    }

    @Test
    public void addSection_emptyAdapter_succeeds() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        StatelessSectionStub sectionStub = addStatelessSectionStubToAdapter(sectionAdapter);

        final String SECTION_TAG = sectionAdapter.addSection(sectionStub);

        assertSame(sectionStub, sectionAdapter.getSection(SECTION_TAG));
        assertSame(sectionStub, sectionAdapter.getSectionsMap().get(SECTION_TAG));
    }

    @Test
    public void addSectionWithTag_emptyAdapter_succeeds() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        StatelessSectionStub sectionStub = addStatelessSectionStubToAdapter(sectionAdapter);

        sectionAdapter.addSection(SECTION_TAG, sectionStub);

        assertSame(sectionStub, sectionAdapter.getSection(SECTION_TAG));
        assertSame(sectionStub, sectionAdapter.getSectionsMap().get(SECTION_TAG));
    }

    @Test
    public void getSectionWithTag_removedSection_returnsNull() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        StatelessSectionStub sectionStub = addStatelessSectionStubToAdapter(sectionAdapter);

        final String SECTION_TAG = sectionAdapter.addSection(sectionStub);
        sectionAdapter.removeSection(SECTION_TAG);

        assertNull(sectionAdapter.getSection(SECTION_TAG));
    }

    @Test
    public void getSectionWithTag_emptyAdapter_returnsNull() {
        assertNull(new SectionedRecyclerViewAdapter().getSection(SECTION_TAG));
    }

    @Test
    public void getItemCount_with4VisiblePlus2InvisibleSections_is44() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        // invisible sections don't increase the total item count
        StatelessSectionStub invisibleSectionStub = addStatelessSectionStubToAdapter(sectionAdapter);
        invisibleSectionStub.setVisible(false);

        HeadedStatelessSectionStub invisibleHeadedSectionSub = addHeadedStatelessSectionStubToAdapter(sectionAdapter);
        invisibleHeadedSectionSub.setVisible(false);

        // 10 + 11 + 11 + 12
        assertThat(sectionAdapter.getItemCount(), is(44));
    }

    @Test
    public void getSectionsMap_with4VisiblePlus2InvisibleSections_hasSize6() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        FootedStatelessSectionStub invisibleFootedSectionSub = addFootedStatelessSectionStubToAdapter(sectionAdapter);
        invisibleFootedSectionSub.setVisible(false);

        HeadedFootedStatelessSectionStub invisibleHeadedFootedSectionSub = addHeadedFootedStatelessSectionStubToAdapter(sectionAdapter);
        invisibleHeadedFootedSectionSub.setVisible(false);

        assertThat(sectionAdapter.getSectionsMap().size(), is(6));
    }

    @Test
    public void getSection_emptyAdapter_isNull() {
        assertNull(new SectionedRecyclerViewAdapter().getSection(SECTION_TAG));
    }

    @Test
    public void getSection_with5Sections_returnsCorrectSection() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();
        StatelessSectionStub sectionStub = addStatelessSectionStubToAdapter(sectionAdapter);

        final String SECTION_TAG = sectionAdapter.addSection(sectionStub);

        assertSame(sectionStub, sectionAdapter.getSection(SECTION_TAG));
    }

    @Test
    public void getSectionForPosition_with4Sections_returnsCorrectValuesForStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        StatelessSectionStub sectionStub = addStatelessSectionStubToAdapter(sectionAdapter);
        addHeadedStatelessSectionStubToAdapter(sectionAdapter);
        addFootedStatelessSectionStubToAdapter(sectionAdapter);
        addHeadedFootedStatelessSectionStubToAdapter(sectionAdapter);

        // StatelessSection - Items from 0 to 9
        assertSame(sectionAdapter.getSectionForPosition(0), sectionStub);
        assertSame(sectionAdapter.getSectionForPosition(9), sectionStub);
    }

    @Test
    public void getSectionForPosition_with4Sections_returnsCorrectValuesForHeadedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        addStatelessSectionStubToAdapter(sectionAdapter);
        HeadedStatelessSectionStub sectionStub = addHeadedStatelessSectionStubToAdapter(sectionAdapter);
        addFootedStatelessSectionStubToAdapter(sectionAdapter);
        addHeadedFootedStatelessSectionStubToAdapter(sectionAdapter);

        // HeadedStatelessSection - Header at 10
        assertSame(sectionAdapter.getSectionForPosition(10), sectionStub);
        // HeadedStatelessSection - Items from 11 to 20
        assertSame(sectionAdapter.getSectionForPosition(11), sectionStub);
        assertSame(sectionAdapter.getSectionForPosition(20), sectionStub);
    }

    @Test
    public void getSectionForPosition_with4Sections_returnsCorrectValuesForFootedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        addStatelessSectionStubToAdapter(sectionAdapter);
        addHeadedStatelessSectionStubToAdapter(sectionAdapter);
        FootedStatelessSectionStub sectionStub = addFootedStatelessSectionStubToAdapter(sectionAdapter);
        addHeadedFootedStatelessSectionStubToAdapter(sectionAdapter);

        // FootedStatelessSection - Items from 21 to 30
        assertSame(sectionAdapter.getSectionForPosition(21), sectionStub);
        assertSame(sectionAdapter.getSectionForPosition(30), sectionStub);
        // FootedStatelessSection - Footer at 31
        assertSame(sectionAdapter.getSectionForPosition(31), sectionStub);
    }

    @Test
    public void getSectionForPosition_with4Sections_returnsCorrectValuesForHeadedFootedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        addStatelessSectionStubToAdapter(sectionAdapter);
        addHeadedStatelessSectionStubToAdapter(sectionAdapter);
        addFootedStatelessSectionStubToAdapter(sectionAdapter);
        HeadedFootedStatelessSectionStub sectionStub = addHeadedFootedStatelessSectionStubToAdapter(sectionAdapter);

        // HeadedFootedStatelessSection - Header at 32
        assertSame(sectionAdapter.getSectionForPosition(32), sectionStub);
        // HeadedFootedStatelessSection - Items from 33 to 42
        assertSame(sectionAdapter.getSectionForPosition(33), sectionStub);
        assertSame(sectionAdapter.getSectionForPosition(42), sectionStub);
        // HeadedFootedStatelessSection - Footer at 43
        assertSame(sectionAdapter.getSectionForPosition(43), sectionStub);
    }

    @Test
    public void getSectionItemViewType_with4Sections_returnsCorrectValuesForStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        // StatelessSection - Items from 0 to 9
        assertThat(sectionAdapter.getSectionItemViewType(0), is(SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED));
        assertThat(sectionAdapter.getSectionItemViewType(9), is(SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED));
    }

    @Test
    public void getSectionItemViewType_with4Sections_returnsCorrectValuesForHeadedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        // HeadedStatelessSection - Header at 10
        assertThat(sectionAdapter.getSectionItemViewType(10), is(SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER));
        // HeadedStatelessSection - Items from 11 to 20
        assertThat(sectionAdapter.getSectionItemViewType(11), is(SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED));
        assertThat(sectionAdapter.getSectionItemViewType(20), is(SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED));
    }

    @Test
    public void getSectionItemViewType_with4Sections_returnsCorrectValuesForFootedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        // FootedStatelessSection - Items from 21 to 30
        assertThat(sectionAdapter.getSectionItemViewType(21), is(SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED));
        assertThat(sectionAdapter.getSectionItemViewType(30), is(SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED));
        // FootedStatelessSection - Footer at 31
        assertThat(sectionAdapter.getSectionItemViewType(31), is(SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER));
    }

    @Test
    public void getSectionItemViewType_with4Sections_returnsCorrectValuesForHeadedFootedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        // HeadedFootedStatelessSection - Header at 32
        assertThat(sectionAdapter.getSectionItemViewType(32), is(SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER));
        // HeadedFootedStatelessSection - Items from 33 to 42
        assertThat(sectionAdapter.getSectionItemViewType(33), is(SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED));
        assertThat(sectionAdapter.getSectionItemViewType(42), is(SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED));
        // HeadedFootedStatelessSection - Footer at 43
        assertThat(sectionAdapter.getSectionItemViewType(43), is(SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER));
    }

    @Test
    public void getItemViewType_with4Sections_returnsCorrectValuesForStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        // StatelessSection [0-4] - Items are type 2
        assertThat(sectionAdapter.getItemViewType(0), is(2));
        assertThat(sectionAdapter.getItemViewType(9), is(2));
    }

    @Test
    public void getItemViewType_with4Sections_returnsCorrectValuesForHeadedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        // HeadedStatelessSection [5-9] - Header is type 5
        assertThat(sectionAdapter.getItemViewType(10), is(5));
        // HeadedStatelessSection [5-9] - Items are type 7
        assertThat(sectionAdapter.getItemViewType(11), is(7));
        assertThat(sectionAdapter.getItemViewType(20), is(7));
    }

    @Test
    public void getItemViewType_with4Sections_returnsCorrectValuesForFootedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        // FootedStatelessSection [10-14] - Items are type 12
        assertThat(sectionAdapter.getItemViewType(21), is(12));
        assertThat(sectionAdapter.getItemViewType(30), is(12));
        // FootedStatelessSection [10-14] - Footer is type 11
        assertThat(sectionAdapter.getItemViewType(31), is(11));
    }

    @Test
    public void getItemViewType_with4Sections_returnsCorrectValuesForHeadedFootedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        // HeadedFootedStatelessSection [15-19] - Header is type 15
        assertThat(sectionAdapter.getItemViewType(32), is(15));
        // HeadedFootedStatelessSection [15-19] - Items are type 17
        assertThat(sectionAdapter.getItemViewType(33), is(17));
        assertThat(sectionAdapter.getItemViewType(42), is(17));
        // HeadedFootedStatelessSection [15-19] - Footer is type 16
        assertThat(sectionAdapter.getItemViewType(43), is(16));
    }

    @Test
    public void onBindViewHolder_with4Sections_onBindIsCalledForStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        BindingStatelessSectionSpy sectionSpy = new BindingStatelessSectionSpy(ITEMS_QTY);
        sectionAdapter.addSection(sectionSpy);

        // Item
        sectionAdapter.onBindViewHolder(null, 0);

        assertTrue(sectionSpy.onBindItemViewHolderWasCalled);
    }

    @Test
    public void onBindViewHolder_with4Sections_onBindIsCalledForHeadedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        BindingHeadedStatelessSectionSpy sectionSpy = new BindingHeadedStatelessSectionSpy(ITEMS_QTY);
        sectionAdapter.addSection(sectionSpy);

        // Header
        sectionAdapter.onBindViewHolder(null, 0);
        // Item
        sectionAdapter.onBindViewHolder(null, 1);

        assertTrue(sectionSpy.onBindHeaderViewHolderWasCalled);
        assertTrue(sectionSpy.onBindItemViewHolderWasCalled);
    }

    @Test
    public void onBindViewHolder_with4Sections_onBindIsCalledForFootedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        BindingFootedStatelessSectionSpy sectionSpy = new BindingFootedStatelessSectionSpy(ITEMS_QTY);
        sectionAdapter.addSection(sectionSpy);

        // Item
        sectionAdapter.onBindViewHolder(null, 0);
        // Footer
        sectionAdapter.onBindViewHolder(null, 10);

        assertTrue(sectionSpy.onBindItemViewHolderWasCalled);
        assertTrue(sectionSpy.onBindFooterViewHolderWasCalled);
    }

    @Test
    public void onBindViewHolder_with4Sections_onBindIsCalledForHeadedFootedStatelessSection() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        BindingHeadedFootedStatelessSectionSpy sectionSpy = new BindingHeadedFootedStatelessSectionSpy(ITEMS_QTY);
        sectionAdapter.addSection(sectionSpy);

        // Header
        sectionAdapter.onBindViewHolder(null, 0);
        // Item
        sectionAdapter.onBindViewHolder(null, 1);
        // Footer
        sectionAdapter.onBindViewHolder(null, 11);

        assertTrue(sectionSpy.onBindHeaderViewHolderWasCalled);
        assertTrue(sectionSpy.onBindItemViewHolderWasCalled);
        assertTrue(sectionSpy.onBindFooterViewHolderWasCalled);
    }

    @Test
    public void onCreateViewHolder_emptyAdapter_returnsNull() {
        assertNull(new SectionedRecyclerViewAdapter().onCreateViewHolder(null, 0));
    }

    @Test(expected = NullPointerException.class)
    public void onCreateViewHolder_withStatelessSection_throwsExceptionForHeader() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        addStatelessSectionStubToAdapter(sectionAdapter);

        sectionAdapter.onCreateViewHolder(null, SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER);
    }

    @Test(expected = NullPointerException.class)
    public void onCreateViewHolder_withStatelessSection_throwsExceptionForFooter() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        addStatelessSectionStubToAdapter(sectionAdapter);

        sectionAdapter.onCreateViewHolder(null, SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER);
    }

    @Test(expected = NullPointerException.class)
    public void onCreateViewHolder_withStatelessSection_throwsExceptionForLoading() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        addStatelessSectionStubToAdapter(sectionAdapter);

        sectionAdapter.onCreateViewHolder(null, SectionedRecyclerViewAdapter.VIEW_TYPE_LOADING);
    }

    @Test(expected = NullPointerException.class)
    public void onCreateViewHolder_withStatelessSection_throwsExceptionForFailed() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        addStatelessSectionStubToAdapter(sectionAdapter);

        sectionAdapter.onCreateViewHolder(null, SectionedRecyclerViewAdapter.VIEW_TYPE_FAILED);
    }

    @Test
    public void removeAllSections_with4Sections_succeeds() {
        SectionedRecyclerViewAdapter sectionAdapter = getAdapterWithFourDifferentSections();

        sectionAdapter.removeAllSections();

        assertThat(sectionAdapter.getItemCount(), is(0));
        assertTrue(sectionAdapter.getSectionsMap().isEmpty());
    }

    private SectionedRecyclerViewAdapter getAdapterWithFourDifferentSections() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        addStatelessSectionStubToAdapter(sectionAdapter);
        addHeadedStatelessSectionStubToAdapter(sectionAdapter);
        addFootedStatelessSectionStubToAdapter(sectionAdapter);
        addHeadedFootedStatelessSectionStubToAdapter(sectionAdapter);

        return sectionAdapter;
    }

    private StatelessSectionStub addStatelessSectionStubToAdapter(SectionedRecyclerViewAdapter sectionAdapter) {
        StatelessSectionStub sectionStub = new StatelessSectionStub(ITEMS_QTY);
        sectionAdapter.addSection(sectionStub);
        return sectionStub;
    }

    private HeadedStatelessSectionStub addHeadedStatelessSectionStubToAdapter(SectionedRecyclerViewAdapter sectionAdapter) {
        HeadedStatelessSectionStub headedSectionSub = new HeadedStatelessSectionStub(ITEMS_QTY);
        sectionAdapter.addSection(headedSectionSub);
        return headedSectionSub;
    }

    private FootedStatelessSectionStub addFootedStatelessSectionStubToAdapter(SectionedRecyclerViewAdapter sectionAdapter) {
        FootedStatelessSectionStub footedSectionSub = new FootedStatelessSectionStub(ITEMS_QTY);
        sectionAdapter.addSection(footedSectionSub);
        return footedSectionSub;
    }

    private HeadedFootedStatelessSectionStub addHeadedFootedStatelessSectionStubToAdapter(SectionedRecyclerViewAdapter sectionAdapter) {
        HeadedFootedStatelessSectionStub headedFootedSectionSub = new HeadedFootedStatelessSectionStub(ITEMS_QTY);
        sectionAdapter.addSection(headedFootedSectionSub);
        return headedFootedSectionSub;
    }
}
