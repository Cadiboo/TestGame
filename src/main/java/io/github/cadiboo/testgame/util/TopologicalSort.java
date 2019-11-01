package io.github.cadiboo.testgame.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class TopologicalSort {

	public static void main(String[] args) {
		Node first = new NamedNode("first");
		Node second = new NamedNode("second");
		Node third = new NamedNode("third");
		first.addEdge(second);
		first.addEdge(third);
		second.addEdge(third);

		Node[] allNodes = {first, second, third};
		sort(Arrays.asList(allNodes));
	}

	public static List<Node> sort(final Collection<Node> allNodes) {
		//L <- Empty list that will contain the sorted elements
		ArrayList<Node> L = new ArrayList<>();

		//S <- Set of all nodes with no incoming edges
		HashSet<Node> S = new HashSet<>();
		for (Node n : allNodes) {
			if (n.inEdges.size() == 0) {
				S.add(n);
			}
		}

		//while S is non-empty do
		while (!S.isEmpty()) {
			//remove a node n from S
			Node n = S.iterator().next();
			S.remove(n);

			//insert n into L
			L.add(n);

			//for each node m with an edge e from n to m do
			for (Iterator<Edge> it = n.outEdges.iterator(); it.hasNext(); ) {
				//remove edge e from the graph
				Edge e = it.next();
				Node m = e.to;
				it.remove();//Remove edge from n
				m.inEdges.remove(e);//Remove edge from m

				//if m has no other incoming edges then insert m into S
				if (m.inEdges.isEmpty()) {
					S.add(m);
				}
			}
		}
		//Check to see if all edges are removed
		for (Node n : allNodes) {
			if (!n.inEdges.isEmpty()) {
				StringBuilder stringBuilder = new StringBuilder("Cycle present, topological sort not possible");
				stringBuilder.append("\n");
				stringBuilder.append("Node: ").append(n);
				stringBuilder.append("\n");
				for (final Edge edge : n.inEdges) {
					stringBuilder.append("Edge: ").append(edge).append(" ").append(edge.from).append(" -> ").append(edge.to);
				}
				throw new IllegalStateException(stringBuilder.toString());
			}
		}
		return L;
	}

	public static class Node {

		final HashSet<Edge> inEdges = new HashSet<>();
		final HashSet<Edge> outEdges = new HashSet<>();

		public void addEdge(Node node) {
			Edge e = new Edge(this, node);
			outEdges.add(e);
			node.inEdges.add(e);
		}

	}

	static class Edge {

		public final Node from;
		public final Node to;

		Edge(Node from, Node to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj.getClass() != Edge.class)
				return false;
			Edge e = (Edge) obj;
			return e.from == from && e.to == to;
		}

	}

	private static class NamedNode extends Node {

		private final String name;

		public NamedNode(final String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	public static class TypedNode<T> extends Node {

		public final T object;

		public TypedNode(final T object) {
			this.object = object;
		}

		@Override
		public String toString() {
			return object.toString();
		}

	}

}
