package nextstep.subway.path;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import nextstep.subway.line.domain.Line;
import nextstep.subway.path.domain.PathFinder;
import nextstep.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("경로 조회에 대한 단위테스트")
class PathFinderTest {

    private Station 대림역;
    private Station 신대방역;
    private Station 신도림역;
    private Line 노선;
    private PathFinder pathFinder;

    @BeforeEach
    void setUp() {
        대림역 = new Station("대림");
        신대방역 = new Station("신대방");
        신도림역 = new Station("신도림");
        노선 = Line.of("2호선", "red", 대림역, 신대방역, 10);
        pathFinder = new PathFinder(Arrays.asList(노선));
    }

    @DisplayName("PathFinder 에 노선을 등록하면 해당 역들도 등록되어야 한다")
    @Test
    void register_vertex_test() {
        // when
        GraphPath<Station, DefaultWeightedEdge> graphPath = pathFinder.findPath(대림역, 신대방역);

        // then
        List<Station> stations = graphPath.getVertexList();
        assertThat(stations).containsExactly(대림역, 신대방역);
    }

    @DisplayName("PathFinder 에 경로를 조회하면 정상적으로 거리와 가중치가 나와야 한다")
    @Test
    void find_path_test() {
        // when
        GraphPath<Station, DefaultWeightedEdge> graphPath = pathFinder.findPath(대림역, 신대방역);

        // then
        assertThat(graphPath.getLength()).isEqualTo(1);
        assertThat(graphPath.getWeight()).isEqualTo(10);
    }

    @DisplayName("1보다 큰 경로를 조회하면 정상적으로 거리와 가중치가 나와야 한다")
    @Test
    void find_path_test2() {
        // given
        노선.registerSection(신대방역, 신도림역, 5);
        pathFinder = new PathFinder(Arrays.asList(노선));

        // when
        GraphPath<Station, DefaultWeightedEdge> graphPath = pathFinder.findPath(대림역, 신도림역);

        // then
        assertThat(graphPath.getLength()).isEqualTo(2);
        assertThat(graphPath.getWeight()).isEqualTo(15);
    }
}